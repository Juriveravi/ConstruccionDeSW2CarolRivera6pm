package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.enums.LoanStatus;
import com.bank.infrastructure.persistence.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaLoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByClientDocumentId(String clientDocumentId);
    List<LoanEntity> findByStatus(LoanStatus status);
}