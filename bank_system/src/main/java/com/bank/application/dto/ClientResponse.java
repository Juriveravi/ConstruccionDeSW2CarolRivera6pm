package com.bank.application.dto;

import com.bank.domain.enums.UserStatus;

public record ClientResponse(
        Long id,
        String documentId,
        String name,
        String identification,
        String email,
        String phone,
        String address,
        UserStatus status,
        String clientType
) {
}
