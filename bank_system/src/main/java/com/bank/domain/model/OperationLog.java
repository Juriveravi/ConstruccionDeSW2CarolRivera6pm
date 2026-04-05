package com.bank.domain.model;

import java.time.LocalDateTime;
import java.util.Map;

public class OperationLog {

    private String id;
    private String operationType;
    private LocalDateTime timestamp;
    private Long userId;
    private String role;
    private String productId;
    private Map<String, Object> details;

    public OperationLog() {}

    public OperationLog(String id, String operationType, LocalDateTime timestamp, Long userId, String role, String productId, Map<String, Object> details) {
        this.id = id;
        this.operationType = operationType;
        this.timestamp = timestamp;
        this.userId = userId;
        this.role = role;
        this.productId = productId;
        this.details = details;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Map<String, Object> getDetails() {
        return details;
    }
    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

}