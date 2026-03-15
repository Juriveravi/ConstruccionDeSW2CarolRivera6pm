package com.bank.domain.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.domain.enums.TransferStatus;

public class Transfer {

    private int id;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private TransferStatus status;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public BankAccount getSourceAccount() {
        return sourceAccount;
    }
    public void setSourceAccount(BankAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }
    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }
    public void setDestinationAccount(BankAccount destinationAccount) {
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

}