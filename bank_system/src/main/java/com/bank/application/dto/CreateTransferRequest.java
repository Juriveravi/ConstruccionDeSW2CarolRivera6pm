package com.bank.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotBlank String originAccount,
        @NotBlank String destinationAccount,
        @NotNull @Positive BigDecimal amount,
        @NotNull Long creatorUserId
) {
}
