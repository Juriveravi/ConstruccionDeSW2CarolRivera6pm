package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.OperationLog;
import com.bank.domain.port.OperationLogRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class OperationLogRepositoryAdapter implements OperationLogRepository {

    private final List<OperationLog> store = new ArrayList<>();

    @Override
    public OperationLog save(OperationLog log) {
        if (log.getId() == null) {
            log.setId(UUID.randomUUID().toString());
        }
        store.add(log);
        return log;
    }

    @Override
    public List<OperationLog> findByAffectedProductId(String productId) {
        return store.stream()
                .filter(l -> productId.equals(l.getProductId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationLog> findAll() {
        return new ArrayList<>(store);
    }
}