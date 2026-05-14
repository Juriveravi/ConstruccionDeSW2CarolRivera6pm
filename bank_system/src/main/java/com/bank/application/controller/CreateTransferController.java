package com.bank.application.controller;

import com.bank.application.dto.CreateTransferRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.TransferResponse;
import com.bank.domain.model.Transfer;
import com.bank.domain.service.CreateTransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class CreateTransferController {

    private final CreateTransferService createTransferService;

    public CreateTransferController(CreateTransferService createTransferService) {
        this.createTransferService = createTransferService;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(@Valid @RequestBody CreateTransferRequest request) {
        Transfer transfer = createTransferService.createTransfer(
                request.originAccount(),
                request.destinationAccount(),
                request.amount(),
                request.creatorUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toTransferResponse(transfer));
    }
}