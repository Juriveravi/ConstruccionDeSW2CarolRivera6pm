package com.bank.domain.service;

import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for querying and retrieving bank accounts.
 * Single Responsibility: Account retrieval and lookup operations.
 */
@Service
public class FindAccountService {

    private final BankAccountRepository bankAccountRepository;

    public FindAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Find a bank account by account number.
     *
     * @param accountNumber the account number to search
     * @return the bank account
     * @throws IllegalArgumentException if account not found
     */
    public BankAccount findByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
    }

    /**
     * Find all bank accounts owned by a specific client.
     *
     * @param documentId the client's document ID
     * @return list of bank accounts
     */
    public List<BankAccount> findByOwner(String documentId) {
        return bankAccountRepository.findByOwnerId(documentId);
    }
}
