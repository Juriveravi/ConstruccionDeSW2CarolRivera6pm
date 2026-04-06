package com.bank.domain.service;

import com.bank.domain.model.OperationLog;
import com.bank.domain.port.OperationLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class OperationLogService {

    private final OperationLogRepository repository;

    public OperationLogService(OperationLogRepository repository) {
        this.repository = repository;
    }

    public void log(
            String operationType,
            Long userId,
            String role,
            String productId,
            Map<String, Object> details
    ) {

        OperationLog log = new OperationLog();

        log.setId(UUID.randomUUID().toString());
        log.setOperationType(operationType);
        log.setTimestamp(LocalDateTime.now());
        log.setUserId(userId);
        log.setRole(role);
        log.setProductId(productId);
        log.setDetails(details);

        repository.save(log);
    }
}