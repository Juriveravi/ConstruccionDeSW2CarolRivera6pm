package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaTransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByStatus(TransferStatus status);

    @Query("SELECT t FROM Transfer t WHERE t.status = 'EN_ESPERA_APROBACION' AND t.creationDate < :threshold")
    List<Transfer> findPendingOlderThan(@Param("threshold") LocalDateTime threshold);
}