package com.bank.domain.repository;

import com.bank.domain.model.BankAccount;

public interface BankAccountRepository {

    BankAccount findByAccountNumber(String accountNumber);

    void save(BankAccount account);

}
