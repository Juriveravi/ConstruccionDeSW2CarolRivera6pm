package com.bank.application.dto;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponse(
        Long id,
        LoanType type,
        String clientDocumentId,
        BigDecimal requestedAmount,
        BigDecimal approvedAmount,
        BigDecimal interestRate,
        int termMonths,
        LoanStatus status,
        LocalDate approvalDate,
        LocalDate disbursementDate,
        String targetAccountNumber
) {
}
