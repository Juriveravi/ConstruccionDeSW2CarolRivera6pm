package com.bank.domain.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.AccountType;
import com.bank.domain.enums.Currency;

public class BankAccount {

    private String accountNumber;
    private Client owner;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDate openingDate;
    private AccountType type;
    private Currency currency;

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public Client getOwner() {
        return owner;
    }
    public void setOwner(Client owner) {
        this.owner = owner;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
    public LocalDate getOpeningDate() {
        return openingDate;
    }
    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
