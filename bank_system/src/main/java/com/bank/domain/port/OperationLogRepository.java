package com.bank.domain.port;

import com.bank.domain.model.OperationLog;
import java.util.List;

public interface OperationLogRepository {
    OperationLog save(OperationLog log);
    List<OperationLog> findByAffectedProductId(String productId);
    List<OperationLog> findAll();
}