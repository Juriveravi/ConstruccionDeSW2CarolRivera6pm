package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.enums.TransferStatus;
import com.bank.domain.model.Transfer;
import com.bank.domain.port.TransferRepository;
import com.bank.infrastructure.persistence.entities.TransferEntity;
import com.bank.infrastructure.persistence.jpa.JpaTransferRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransferRepositoryAdapter implements TransferRepository {

    private final JpaTransferRepository jpa;

    public TransferRepositoryAdapter(JpaTransferRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Transfer save(Transfer transfer) {
        return toDomain(jpa.save(toEntity(transfer)));
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Transfer> findByStatus(TransferStatus status) {
        return jpa.findByStatus(status).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Transfer> findPendingOlderThan(LocalDateTime threshold) {
        return jpa.findPendingOlderThan(threshold).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private TransferEntity toEntity(Transfer t) {
        TransferEntity e = new TransferEntity();
        e.setId(t.getId());
        e.setOriginAccount(t.getOriginAccount());
        e.setDestinationAccount(t.getDestinationAccount());
        e.setAmount(t.getAmount());
        e.setCreationDate(t.getCreationDate());
        e.setApprovalDate(t.getApprovalDate());
        e.setStatus(t.getStatus());
        e.setCreatorUserId(t.getCreatorUserId());
        e.setApproverUserId(t.getApproverUserId());
        return e;
    }

    private Transfer toDomain(TransferEntity e) {
        Transfer t = new Transfer();
        t.setId(e.getId());
        t.setOriginAccount(e.getOriginAccount());
        t.setDestinationAccount(e.getDestinationAccount());
        t.setAmount(e.getAmount());
        t.setCreationDate(e.getCreationDate());
        t.setApprovalDate(e.getApprovalDate());
        t.setStatus(e.getStatus());
        t.setCreatorUserId(e.getCreatorUserId());
        t.setApproverUserId(e.getApproverUserId());
        return t;
    }
}