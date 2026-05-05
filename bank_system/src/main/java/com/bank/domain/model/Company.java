package com.bank.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COMPANY")
public class Company extends Client {

    @Column(name = "business_name")
    private String businessName;

    @ManyToOne
    @JoinColumn(name = "legal_representative_id")
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