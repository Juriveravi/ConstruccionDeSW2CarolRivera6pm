package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.TransferRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CreateTransferService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;

    @Value("${bank.transfer.approval-threshold:5000000}")
    private BigDecimal approvalThreshold;

    public CreateTransferService(TransferRepository transferRepository,
                                 BankAccountRepository bankAccountRepository) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public Transfer createTransfer(String originAccountNumber, String destinationAccountNumber,
                                   BigDecimal amount, Long creatorUserId) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }

        BankAccount origin = getActiveAccount(originAccountNumber);

        Transfer transfer = new Transfer();
        transfer.setOriginAccount(originAccountNumber);
        transfer.setDestinationAccount(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setCreationDate(LocalDateTime.now());
        transfer.setCreatorUserId(creatorUserId);

        // Transfers above threshold require approval; below threshold execute immediately
        if (amount.compareTo(approvalThreshold) > 0) {
            transfer.setStatus(TransferStatus.PENDING_APPROVAL);
        } else {
            // Validate sufficient funds and execute immediately
            if (origin.getBalance().compareTo(amount) < 0) {
                throw new IllegalStateException("Insufficient funds in account: " + originAccountNumber);
            }
            transfer.setStatus(TransferStatus.EXECUTED);
            transfer.setApprovalDate(LocalDateTime.now());
            // Update balances
            origin.setBalance(origin.getBalance().subtract(amount));
            bankAccountRepository.save(origin);

            bankAccountRepository.findByAccountNumber(destinationAccountNumber).ifPresent(dest -> {
                dest.setBalance(dest.getBalance().add(amount));
                bankAccountRepository.save(dest);
            });
        }

        return transferRepository.save(transfer);
    }

    public Transfer getTransfer(Long transferId) {
        return transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found: " + transferId));
    }

    private BankAccount getActiveAccount(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
        if (account.getStatus() == AccountStatus.BLOCKED || account.getStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("The account " + accountNumber + " is not operative.");
        }
        return account;
    }
}