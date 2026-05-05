package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service responsible for approving and rejecting transfers.
 * Single Responsibility: Transfer approval operations.
 */
@Service
public class ApproveTransferService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;

    public ApproveTransferService(TransferRepository transferRepository,
                                  BankAccountRepository bankAccountRepository) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Approve a pending transfer.
     *
     * @param transferId the transfer ID to approve
     * @return the approved transfer
     * @throws IllegalArgumentException if transfer not found
     * @throws IllegalStateException if transfer is not pending or origin account has insufficient balance
     */
    @Transactional
    public Transfer approveTransfer(Long transferId) {
        Transfer transfer = getTransfer(transferId);

        if (transfer.getStatus() != TransferStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("The transfer is not pending approval.");
        }

        BankAccount origin = getActiveAccount(transfer.getOriginAccount());

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance in origin account.");
        }

        transfer.setApprovalDate(LocalDateTime.now());

        return transferRepository.save(transfer);
    }

    /**
     * Reject a pending transfer.
     *
     * @param transferId the transfer ID to reject
     * @return the rejected transfer
     * @throws IllegalArgumentException if transfer not found
     * @throws IllegalStateException if transfer is not pending approval
     */
    @Transactional
    public Transfer rejectTransfer(Long transferId) {
        Transfer transfer = getTransfer(transferId);

        if (transfer.getStatus() != TransferStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("The transfer is not pending approval.");
        }

        transfer.setStatus(TransferStatus.REJECTED);

        return transferRepository.save(transfer);
    }

    /**
     * Retrieve a transfer by its ID.
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
