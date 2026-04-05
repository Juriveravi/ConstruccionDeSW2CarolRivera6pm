package com.bank.domain.port;

import com.bank.domain.model.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    BankAccount save(BankAccount account);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    List<BankAccount> findByOwnerId(String documentId);
    boolean existsByAccountNumber(String accountNumber);
  
}