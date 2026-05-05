package com.bank.domain.service;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service responsible for withdrawing money from bank accounts.
 * Single Responsibility: Withdrawal operations.
 */
@Service
public class WithdrawMoneyService {

    private final BankAccountRepository bankAccountRepository;

    public WithdrawMoneyService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Withdraw money from an account.
     *
     * @param accountNumber the account number to withdraw from
     * @param amount the amount to withdraw
     * @return the updated bank account
     * @throws IllegalArgumentException if account not found or amount is invalid
     * @throws IllegalStateException if account is not active or insufficient balance
     */
    @Transactional
    public BankAccount withdrawMoney(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero.");
        }

        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("The account " + accountNumber + " is not operative.");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance in account: " + accountNumber);
        }

        account.setBalance(account.getBalance().subtract(amount));
        return bankAccountRepository.save(account);
    }
}
