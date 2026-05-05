package com.bank.application.controller;

import com.bank.application.dto.CreateTransferRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.TransferResponse;
import com.bank.domain.model.Transfer;
import com.bank.domain.service.CreateTransferService;
import com.bank.domain.service.ApproveTransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final CreateTransferService createTransferService;
    private final ApproveTransferService approveTransferService;

    public TransferController(CreateTransferService createTransferService,
                             ApproveTransferService approveTransferService) {
        this.createTransferService = createTransferService;
        this.approveTransferService = approveTransferService;
    }

    /** POST /api/transfers — Crea una transferencia */
    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(@Valid @RequestBody CreateTransferRequest request) {
        Transfer transfer = createTransferService.createTransfer(
                request.originAccount(),
                request.destinationAccount(),
                request.amount(),
                request.creatorUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toTransferResponse(transfer));
    }

    /** GET /api/transfers/{id} — Consulta una transferencia */
    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toTransferResponse(createTransferService.getTransfer(id)));
    }

    /** PATCH /api/transfers/{id}/approve — Aprueba (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<TransferResponse> approveTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toTransferResponse(approveTransferService.approveTransfer(id)));
    }

    /** PATCH /api/transfers/{id}/reject — Rechaza (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<TransferResponse> rejectTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toTransferResponse(approveTransferService.rejectTransfer(id)));
    }
}