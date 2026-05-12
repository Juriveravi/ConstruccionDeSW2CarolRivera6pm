package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.enums.TransferStatus;
import com.bank.infrastructure.persistence.entities.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface JpaTransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findByStatus(TransferStatus status);

    @Query("SELECT t FROM TransferEntity t WHERE t.status = 'PENDING_APPROVAL' AND t.creationDate < :threshold")
    List<TransferEntity> findPendingOlderThan(@Param("threshold") LocalDateTime threshold);
}