package com.bank.domain.port;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findByClientDocumentId(String documentId);
    List<Loan> findByStatus(LoanStatus status);
}