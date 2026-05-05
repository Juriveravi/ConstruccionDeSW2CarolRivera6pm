package com.bank.application.dto;

import com.bank.domain.enums.TransferStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(
        Long id,
        String originAccount,
        String destinationAccount,
        BigDecimal amount,
        LocalDateTime creationDate,
        LocalDateTime approvalDate,
        TransferStatus status,
        Long creatorUserId,
        Long approverUserId
) {
}
