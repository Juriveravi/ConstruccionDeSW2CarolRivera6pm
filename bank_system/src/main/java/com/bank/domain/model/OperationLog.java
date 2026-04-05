package com.bank.domain.model;

import java.time.LocalDateTime;
import java.util.Map;

public class OperationLog {

    private String id;
    private String operationType;
    private LocalDateTime timestamp;
    private int userId;
    private Map<String, Object> details;
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
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Map<String, Object> getDetails() {
        return details;
    }
    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

}