package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.TransferResponse;
import com.bank.domain.model.Transfer;
import com.bank.domain.service.ApproveTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class RejectTransferController {

    private final ApproveTransferService approveTransferService;

    public RejectTransferController(ApproveTransferService approveTransferService) {
        this.approveTransferService = approveTransferService;
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<TransferResponse> rejectTransfer(@PathVariable Long id) {
        Transfer transfer = approveTransferService.rejectTransfer(id);
        return ResponseEntity.ok(DtoMapper.toTransferResponse(transfer));
    }
}