package com.bank.application.dto;

import com.bank.domain.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateClientStatusRequest(
        @NotNull UserStatus status
) {
}
