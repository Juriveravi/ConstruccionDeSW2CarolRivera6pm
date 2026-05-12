package com.bank.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record MoneyOperationRequest(
        @NotNull @Positive BigDecimal amount
) {
}