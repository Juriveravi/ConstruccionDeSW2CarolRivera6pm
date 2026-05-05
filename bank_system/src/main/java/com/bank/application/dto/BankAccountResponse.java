package com.bank.application.dto;

import com.bank.domain.enums.AccountStatus;
import com.bank.domain.enums.AccountType;
import com.bank.domain.enums.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BankAccountResponse(
        Long id,
        String accountNumber,
        AccountType type,
        String ownerId,
        BigDecimal balance,
        Currency currency,
        AccountStatus status,
        LocalDate openingDate
) {
}
