package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.OperationLog;
import com.bank.domain.port.OperationLogRepository;
import com.bank.infrastructure.persistence.entities.OperationLogDocument;
import com.bank.infrastructure.persistence.mongo.MongoOperationLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationLogRepositoryAdapter implements OperationLogRepository {

    private final MongoOperationLogRepository mongo;

    public OperationLogRepositoryAdapter(MongoOperationLogRepository mongo) {
        this.mongo = mongo;
    }

    @Override
    public OperationLog save(OperationLog log) {
        OperationLogDocument doc = toDocument(log);
        OperationLogDocument saved = mongo.save(doc);
        return toDomain(saved);
    }

    @Override
    public List<OperationLog> findByAffectedProductId(String productId) {
        return mongo.findByProductId(productId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationLog> findAll() {
        return mongo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private OperationLogDocument toDocument(OperationLog log) {
        OperationLogDocument doc = new OperationLogDocument();
        doc.setId(log.getId());
        doc.setOperationType(log.getOperationType());
        doc.setTimestamp(log.getTimestamp());
        doc.setUserId(log.getUserId());
        doc.setRole(log.getRole());
        doc.setProductId(log.getProductId());
        doc.setDetails(log.getDetails());
        return doc;
    }

    private OperationLog toDomain(OperationLogDocument doc) {
        OperationLog log = new OperationLog();
        log.setId(doc.getId());
        log.setOperationType(doc.getOperationType());
        log.setTimestamp(doc.getTimestamp());
        log.setUserId(doc.getUserId());
        log.setRole(doc.getRole());
        log.setProductId(doc.getProductId());
        log.setDetails(doc.getDetails());
        return log;
    }
}