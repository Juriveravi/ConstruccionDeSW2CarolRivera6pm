package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.Loan;
import com.bank.domain.port.LoanRepository;
import com.bank.infrastructure.persistence.jpa.JpaLoanRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoanRepositoryAdapter implements LoanRepository {

    private final JpaLoanRepository jpa;

    public LoanRepositoryAdapter(JpaLoanRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Loan save(Loan loan) {
        return jpa.save(loan);
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<Loan> findByClientDocumentId(String documentId) {
        return jpa.findByClientDocumentId(documentId);
    }

    @Override
    public List<Loan> findByStatus(LoanStatus status) {
        return jpa.findByStatus(status);
    }
}