package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaBankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    List<BankAccount> findByOwnerId(String ownerId);
    boolean existsByAccountNumber(String accountNumber);
}