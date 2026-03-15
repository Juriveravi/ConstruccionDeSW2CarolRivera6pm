package com.bank.domain.model;

import java.math.BigDecimal;

import com.bank.domain.enums.LoanStatus;

public class Loan {

    private int id;
    private Client applicant;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private LoanStatus status;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Client getApplicant() {
        return applicant;
    }
    public void setApplicant(Client applicant) {
        this.applicant = applicant;
    }
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }
    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
    public LoanStatus getStatus() {
        return status;
    }
    public void setStatus(LoanStatus status) {
        this.status = status;
    }

}