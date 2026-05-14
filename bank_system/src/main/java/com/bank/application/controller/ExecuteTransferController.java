package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.TransferResponse;
import com.bank.domain.model.Transfer;
import com.bank.domain.service.TransferExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class ExecuteTransferController {

    private final TransferExecutionService transferExecutionService;

    public ExecuteTransferController(TransferExecutionService transferExecutionService) {
        this.transferExecutionService = transferExecutionService;
    }

    @PatchMapping("/{id}/execute")
    public ResponseEntity<TransferResponse> executeTransfer(@PathVariable Long id) {
        Transfer transfer = transferExecutionService.executeTransfer(id);
        return ResponseEntity.ok(DtoMapper.toTransferResponse(transfer));
    }
}