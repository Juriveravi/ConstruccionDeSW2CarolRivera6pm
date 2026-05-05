package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.BankAccountRepository;
import com.bank.domain.port.TransferRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsible for executing transfers and managing their lifecycle.
 * Single Responsibility: Transfer execution and expiration logic.
 */
@Service
public class TransferExecutionService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransferExecutionService(TransferRepository transferRepository,
                                   BankAccountRepository bankAccountRepository) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Execute a pending approval transfer, moving funds between accounts.
     *
     * @param transferId the transfer ID to execute
     * @return the executed transfer
     * @throws IllegalArgumentException if transfer or accounts not found
     * @throws IllegalStateException if transfer is not pending approval or origin account lacks balance
     */
    @Transactional
    public Transfer executeTransfer(Long transferId) {
        Transfer transfer = getTransfer(transferId);

        if (transfer.getStatus() != TransferStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only pending approval transfers can be executed.");
        }

        BankAccount origin = getActiveAccount(transfer.getOriginAccount());

        if (origin.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance in origin account.");
        }

        // Deduct from origin account
        origin.setBalance(origin.getBalance().subtract(transfer.getAmount()));
        bankAccountRepository.save(origin);

        // Add to destination account if it exists
        bankAccountRepository.findByAccountNumber(transfer.getDestinationAccount()).ifPresent(destination -> {
            destination.setBalance(destination.getBalance().add(transfer.getAmount()));
            bankAccountRepository.save(destination);
        });

        transfer.setStatus(TransferStatus.EXECUTED);

        return transferRepository.save(transfer);
    }

    /**
     * Scheduled task to expire pending transfers older than 60 minutes.
     * Runs every minute to check for stale transfers.
     */
    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void expireStaleTransfers() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(60);
        List<Transfer> staleTransfers = transferRepository.findPendingOlderThan(threshold);

        for (Transfer transfer : staleTransfers) {
            transfer.setStatus(TransferStatus.EXPIRED);
            transferRepository.save(transfer);
        }
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
