package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByClientDocumentId(String clientDocumentId);
    List<Loan> findByStatus(LoanStatus status);
}