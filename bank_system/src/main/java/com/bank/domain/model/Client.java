package com.bank.domain.model;

import com.bank.domain.enums.UserStatus;

public abstract class Client {
  protected String identification;
    protected String email;
    protected String phone;
    protected String address;
    protected String name;
    protected String documentId;
    protected UserStatus status;

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
