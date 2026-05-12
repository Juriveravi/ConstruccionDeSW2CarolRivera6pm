package com.bank.infrastructure.persistence.mongo;

import com.bank.infrastructure.persistence.entities.OperationLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoOperationLogRepository extends MongoRepository<OperationLogDocument, String> {
    List<OperationLogDocument> findByProductId(String productId);
}