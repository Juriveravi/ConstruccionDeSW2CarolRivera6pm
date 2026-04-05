package com.bank.domain.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.domain.enums.TransferStatus;

public class Transfer {

    private Long id;
    private String originAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private TransferStatus status;
    private Long creatorUserId;
    private LocalDateTime approvalDate;
    private Long approverUserId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOriginAccount() {
        return originAccount;
    }
    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }
    public String getDestinationAccount() {
        return destinationAccount;
    }
    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public TransferStatus getStatus() {
        return status;
    }
    public void setStatus(TransferStatus status) {
        this.status = status;
    }
    public Long getCreatorUserId() {
        return creatorUserId;
    }
    public void setCreatorUserId(Long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }
    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }
    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }
    public Long getApproverUserId() {
        return approverUserId;
    }
    public void setApproverUserId(Long approverUserId) {
        this.approverUserId = approverUserId;
    }

}