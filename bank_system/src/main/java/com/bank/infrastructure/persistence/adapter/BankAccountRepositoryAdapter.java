package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountRepository;
import com.bank.infrastructure.persistence.entities.BankAccountEntity;
import com.bank.infrastructure.persistence.jpa.JpaBankAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BankAccountRepositoryAdapter implements BankAccountRepository {

    private final JpaBankAccountRepository jpa;

    public BankAccountRepositoryAdapter(JpaBankAccountRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public BankAccount save(BankAccount account) {
        return toDomain(jpa.save(toEntity(account)));
    }

    @Override
    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return jpa.findByAccountNumber(accountNumber).map(this::toDomain);
    }

    @Override
    public List<BankAccount> findByOwnerId(String documentId) {
        return jpa.findByOwnerId(documentId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return jpa.existsByAccountNumber(accountNumber);
    }

    private BankAccountEntity toEntity(BankAccount a) {
        BankAccountEntity e = new BankAccountEntity();
        e.setId(a.getId());
        e.setAccountNumber(a.getAccountNumber());
        e.setType(a.getType());
        e.setOwnerId(a.getOwnerId());
        e.setBalance(a.getBalance());
        e.setCurrency(a.getCurrency());
        e.setStatus(a.getStatus());
        e.setOpeningDate(a.getOpeningDate());
        return e;
    }

    private BankAccount toDomain(BankAccountEntity e) {
        BankAccount a = new BankAccount();
        a.setId(e.getId());
        a.setAccountNumber(e.getAccountNumber());
        a.setType(e.getType());
        a.setOwnerId(e.getOwnerId());
        a.setBalance(e.getBalance());
        a.setCurrency(e.getCurrency());
        a.setStatus(e.getStatus());
        a.setOpeningDate(e.getOpeningDate());
        return a;
    }
}