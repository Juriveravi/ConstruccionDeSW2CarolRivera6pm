package com.bank.domain.model;

public class Company extends Client {

    private String businessName;
    private NaturalPerson legalRepresentative;
    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public NaturalPerson getLegalRepresentative() {
        return legalRepresentative;
    }
    public void setLegalRepresentative(NaturalPerson legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

}