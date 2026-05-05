package com.bank.application.controller;

import com.bank.domain.model.Transfer;
import com.bank.domain.service.CreateTransferService;
import com.bank.domain.service.ApproveTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    public ResponseEntity<Transfer> createTransfer(@RequestBody TransferRequest request) {
        Transfer transfer = createTransferService.createTransfer(
                request.originAccount(),
                request.destinationAccount(),
                request.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    /** GET /api/transfers/{id} — Consulta una transferencia */
    @GetMapping("/{id}")
    public ResponseEntity<Transfer> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(createTransferService.getTransfer(id));
    }

    /** PATCH /api/transfers/{id}/approve — Aprueba (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Transfer> approveTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(approveTransferService.approveTransfer(id));
    }

    /** PATCH /api/transfers/{id}/reject — Rechaza (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Transfer> rejectTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(approveTransferService.rejectTransfer(id));
    }

    // ── Request DTO ──────────────────────────────────────────────────────────
    public record TransferRequest(String originAccount, String destinationAccount,
                                  BigDecimal amount) {}
}