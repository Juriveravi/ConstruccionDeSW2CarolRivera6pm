package com.bank.domain.model;

import java.time.LocalDate;

public class NaturalPerson extends Client {

    private String fullName;
    private LocalDate birthDate;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}