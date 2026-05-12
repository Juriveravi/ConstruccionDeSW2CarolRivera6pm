package com.bank.domain.model;

import com.bank.domain.enums.UserRole;
import com.bank.domain.enums.UserStatus;

public class User {

    private Long id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String phone;
    private String address;
    private UserRole role;
    private UserStatus status;
    private String associatedClientId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
    public String getAssociatedClientId() { return associatedClientId; }
    public void setAssociatedClientId(String associatedClientId) { this.associatedClientId = associatedClientId; }
}