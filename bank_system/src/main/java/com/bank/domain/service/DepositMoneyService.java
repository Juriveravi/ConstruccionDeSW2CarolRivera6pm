package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service responsible for depositing money into bank accounts.
 * Single Responsibility: Deposit operations.
 */
@Service
public class DepositMoneyService {

    private final BankAccountRepository bankAccountRepository;

    public DepositMoneyService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Deposit money into an account.
     *
     * @param accountNumber the account number to deposit to
     * @param amount the amount to deposit
     * @return the updated bank account
     * @throws IllegalArgumentException if account not found
     * @throws IllegalStateException if account is not active or amount is invalid
     */
    @Transactional
    public BankAccount depositMoney(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }

        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("The account " + accountNumber + " is not operative.");
        }

        account.setBalance(account.getBalance().add(amount));
        return bankAccountRepository.save(account);
    }
}
