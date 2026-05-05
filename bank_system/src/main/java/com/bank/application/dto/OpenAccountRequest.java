package com.bank.application.dto;

import com.bank.domain.enums.AccountType;
import com.bank.domain.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OpenAccountRequest(
        @NotBlank String clientDocumentId,
        @NotNull AccountType accountType,
        @NotNull Currency currency
) {
}
