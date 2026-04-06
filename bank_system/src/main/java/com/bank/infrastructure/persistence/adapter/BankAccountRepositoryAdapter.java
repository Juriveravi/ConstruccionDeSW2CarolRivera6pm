package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountRepository;
import com.bank.infrastructure.persistence.jpa.JpaBankAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BankAccountRepositoryAdapter implements BankAccountRepository {

    private final JpaBankAccountRepository jpa;

    public BankAccountRepositoryAdapter(JpaBankAccountRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public BankAccount save(BankAccount account) {
        return jpa.save(account);
    }

    @Override
    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return jpa.findByAccountNumber(accountNumber);
    }

    @Override
    public List<BankAccount> findByOwnerId(String documentId) {
        return jpa.findByOwnerId(documentId);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return jpa.existsByAccountNumber(accountNumber);
    }
}