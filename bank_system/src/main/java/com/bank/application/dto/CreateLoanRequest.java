package com.bank.application.dto;

import com.bank.domain.enums.LoanType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateLoanRequest(
        @NotBlank String clientDocumentId,
        @NotNull LoanType loanType,
        @NotNull @Positive BigDecimal requestedAmount,
        @Min(1) int termMonths
) {
}
