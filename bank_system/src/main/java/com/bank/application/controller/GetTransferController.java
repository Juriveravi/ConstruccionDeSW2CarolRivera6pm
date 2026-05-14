package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.TransferResponse;
import com.bank.domain.service.CreateTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class GetTransferController {

    private final CreateTransferService createTransferService;

    public GetTransferController(CreateTransferService createTransferService) {
        this.createTransferService = createTransferService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toTransferResponse(createTransferService.getTransfer(id)));
    }
}