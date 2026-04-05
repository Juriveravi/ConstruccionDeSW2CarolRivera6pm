package com.bank.domain.port;

import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.Transfer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransferRepository {
    Transfer save(Transfer transfer);
    Optional<Transfer> findById(Long id);
    List<Transfer> findByStatus(TransferStatus status);
    List<Transfer> findPendingOlderThan(LocalDateTime threshold);
}