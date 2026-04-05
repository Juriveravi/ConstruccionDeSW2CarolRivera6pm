package com.bank.domain.model;

import com.bank.domain.enums.ProductCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "banking_products")
public class BankingProduct {

    @Id
    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ProductCategory category;

    @Column(name = "requires_approval", nullable = false)
    private boolean requiresApproval;

    public BankingProduct() {}

    public BankingProduct(String productCode, String productName, ProductCategory category, boolean requiresApproval) {
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.requiresApproval = requiresApproval;
    }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public boolean isRequiresApproval() { return requiresApproval; }
    public void setRequiresApproval(boolean requiresApproval) { this.requiresApproval = requiresApproval; }
}
