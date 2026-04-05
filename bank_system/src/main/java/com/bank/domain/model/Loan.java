package com.bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;

public class Loan {

    private Long id;
    private LoanType type;
    private String clientDocumentId;
    private BigDecimal requestedAmount;
    private int termMonths;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private LoanStatus status;
    private LocalDate approvalDate;
    private String targetAccountNumber;
    private LocalDate disbursementDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LoanType getType() {
        return type;
    }
    public void setType(LoanType type) {
        this.type = type;
    }
    public String getClientDocumentId() {
        return clientDocumentId;
    }
    public void setClientDocumentId(String clientDocumentId) {
        this.clientDocumentId = clientDocumentId;
    }
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    public int getTermMonths() {
        return termMonths;
    }
    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }
    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }
    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
    public BigDecimal getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    public LoanStatus getStatus() {
        return status;
    }
    public void setStatus(LoanStatus status) {
        this.status = status;
    }
    public LocalDate getApprovalDate() {
        return approvalDate;
    }
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }
    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }
    public void setDisbursementDate(LocalDate disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

}