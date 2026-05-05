package com.bank.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(
        @NotBlank String documentId,
        @NotBlank String name,
        @NotBlank String identification,
        @NotBlank @Email String email,
        @NotBlank String phone,
        @NotBlank String address
) {
}
