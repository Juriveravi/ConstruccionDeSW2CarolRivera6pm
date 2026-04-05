package com.bank.application.controller;

import com.bank.domain.model.Transfer;
import com.bank.domain.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    /** POST /api/transfers — Crea una transferencia */
    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody TransferRequest request) {
        Transfer transfer = transferService.createTransfer(
                request.originAccount(),
                request.destinationAccount(),
                request.amount(),
                request.creatorUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    /** GET /api/transfers/{id} — Consulta una transferencia */
    @GetMapping("/{id}")
    public ResponseEntity<Transfer> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(transferService.getTransfer(id));
    }

    /** PATCH /api/transfers/{id}/approve — Aprueba (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Transfer> approveTransfer(@PathVariable Long id,
                                                    @RequestParam Long approverUserId) {
        return ResponseEntity.ok(transferService.approveTransfer(id, approverUserId));
    }

    /** PATCH /api/transfers/{id}/reject — Rechaza (solo Supervisor de Empresa) */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Transfer> rejectTransfer(@PathVariable Long id,
                                                   @RequestParam Long approverUserId) {
        return ResponseEntity.ok(transferService.rejectTransfer(id, approverUserId));
    }

    // ── Request DTO ──────────────────────────────────────────────────────────
    public record TransferRequest(String originAccount, String destinationAccount,
                                  BigDecimal amount, Long creatorUserId) {}
}