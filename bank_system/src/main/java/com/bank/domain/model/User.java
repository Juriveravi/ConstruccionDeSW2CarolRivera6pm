package com.bank.domain.model;

import com.bank.domain.enums.UserRole;

public class User {

    private int id;
    private String fullName;
    private String email;
    private UserRole role;
    private Client associatedClient;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public Client getAssociatedClient() {
        return associatedClient;
    }
    public void setAssociatedClient(Client associatedClient) {
        this.associatedClient = associatedClient;
    }

}