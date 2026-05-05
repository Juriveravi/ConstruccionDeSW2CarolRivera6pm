package com.bank.domain.service;

import com.bank.domain.model.OperationLog;
import com.bank.domain.port.OperationLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for retrieving customer operation history.
 * Single Responsibility: Customer history queries.
 */
@Service
public class FindCustomerHistoryService {

    private final OperationLogRepository operationLogRepository;

    public FindCustomerHistoryService(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * Get the operation history for a specific customer/entity.
     *
     * @param productId the customer or entity ID
     * @return list of operation logs
     */
    public List<OperationLog> getCustomerHistory(String productId) {
        return operationLogRepository.findByAffectedProductId(productId);
    }
}
