package com.bank.infrastructure.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "operation_logs")
public class OperationLogDocument {

    @Id
    private String id;

    @Field("operation_type")
    private String operationType;

    @Field("timestamp")
    private LocalDateTime timestamp;

    @Field("user_id")
    private Long userId;

    @Field("role")
    private String role;

    @Field("product_id")
    private String productId;

    @Field("details")
    private Map<String, Object> details;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
}