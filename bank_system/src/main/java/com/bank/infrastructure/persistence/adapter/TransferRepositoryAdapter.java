package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.TransferRepository;
import com.bank.infrastructure.persistence.jpa.JpaTransferRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TransferRepositoryAdapter implements TransferRepository {

    private final JpaTransferRepository jpa;

    public TransferRepositoryAdapter(JpaTransferRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Transfer save(Transfer transfer) {
        return jpa.save(transfer);
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<Transfer> findByStatus(TransferStatus status) {
        return jpa.findByStatus(status);
    }

    @Override
    public List<Transfer> findPendingOlderThan(LocalDateTime threshold) {
        return jpa.findPendingOlderThan(threshold);
    }
}