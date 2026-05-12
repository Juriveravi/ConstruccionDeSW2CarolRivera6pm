package com.bank.infrastructure.persistence.jpa;

import com.bank.infrastructure.persistence.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaBankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);
    List<BankAccountEntity> findByOwnerId(String ownerId);
    boolean existsByAccountNumber(String accountNumber);
}