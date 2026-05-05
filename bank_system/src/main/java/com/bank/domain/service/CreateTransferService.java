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

/**
 * Service responsible for creating transfers.
 * Single Responsibility: Transfer creation operations.
 */
@Service
public class CreateTransferService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;

    /** Maximum amount without approval (configurable in application.properties) */
    @Value("${bank.transfer.approval-threshold:5000000}")
    private BigDecimal approvalThreshold;

    public CreateTransferService(TransferRepository transferRepository,
                                 BankAccountRepository bankAccountRepository) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Create a transfer request between two accounts.
     *
     * @param originAccountNumber the source account number
     * @param destinationAccountNumber the destination account number
     * @param amount the transfer amount
     * @return the created transfer with PENDING_APPROVAL status
     * @throws IllegalArgumentException if origin account not found
     * @throws IllegalStateException if amount is invalid or account is not active
     */
    @Transactional
    public Transfer createTransfer(String originAccountNumber, String destinationAccountNumber,
                                   BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }

        getActiveAccount(originAccountNumber);

        Transfer transfer = new Transfer();
        transfer.setOriginAccount(originAccountNumber);
        transfer.setDestinationAccount(destinationAccountNumber);
        transfer.setAmount(amount);
        transfer.setCreationDate(LocalDateTime.now());
        transfer.setStatus(TransferStatus.PENDING_APPROVAL);

        return transferRepository.save(transfer);
    }

    /**
     * Get a transfer by its ID.
     *
     * @param transferId the transfer ID
     * @return the transfer
     * @throws IllegalArgumentException if transfer not found
     */
    public Transfer getTransfer(Long transferId) {
        return transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found: " + transferId));
    }

    /**
     * Get an active bank account.
     *
     * @param accountNumber the account number
     * @return the active bank account
     * @throws IllegalArgumentException if account not found
     * @throws IllegalStateException if account is blocked or cancelled
     */
    private BankAccount getActiveAccount(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
        if (account.getStatus() == AccountStatus.BLOCKED || account.getStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("The account " + accountNumber + " is not operative.");
        }
        return account;
    }
}
