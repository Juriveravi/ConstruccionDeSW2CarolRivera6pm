package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.Loan;
import com.bank.domain.port.LoanRepository;
import com.bank.infrastructure.persistence.entities.LoanEntity;
import com.bank.infrastructure.persistence.jpa.JpaLoanRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoanRepositoryAdapter implements LoanRepository {

    private final JpaLoanRepository jpa;

    public LoanRepositoryAdapter(JpaLoanRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Loan save(Loan loan) {
        return toDomain(jpa.save(toEntity(loan)));
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Loan> findByClientDocumentId(String documentId) {
        return jpa.findByClientDocumentId(documentId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByStatus(LoanStatus status) {
        return jpa.findByStatus(status).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private LoanEntity toEntity(Loan l) {
        LoanEntity e = new LoanEntity();
        e.setId(l.getId());
        e.setType(l.getType());
        e.setClientDocumentId(l.getClientDocumentId());
        e.setRequestedAmount(l.getRequestedAmount());
        e.setApprovedAmount(l.getApprovedAmount());
        e.setInterestRate(l.getInterestRate());
        e.setTermMonths(l.getTermMonths());
        e.setStatus(l.getStatus());
        e.setApprovalDate(l.getApprovalDate());
        e.setDisbursementDate(l.getDisbursementDate());
        e.setTargetAccountNumber(l.getTargetAccountNumber());
        return e;
    }

    private Loan toDomain(LoanEntity e) {
        Loan l = new Loan();
        l.setId(e.getId());
        l.setType(e.getType());
        l.setClientDocumentId(e.getClientDocumentId());
        l.setRequestedAmount(e.getRequestedAmount());
        l.setApprovedAmount(e.getApprovedAmount());
        l.setInterestRate(e.getInterestRate());
        l.setTermMonths(e.getTermMonths());
        l.setStatus(e.getStatus());
        l.setApprovalDate(e.getApprovalDate());
        l.setDisbursementDate(e.getDisbursementDate());
        l.setTargetAccountNumber(e.getTargetAccountNumber());
        return l;
    }
}