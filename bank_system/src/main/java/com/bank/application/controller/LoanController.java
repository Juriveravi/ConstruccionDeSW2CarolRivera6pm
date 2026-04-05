package com.bank.application.controller;

import com.bank.domain.enums.LoanStatus;
import com.bank.domain.enums.LoanType;
import com.bank.domain.model.Loan;
import com.bank.domain.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /** POST /api/loans — Solicita un nuevo préstamo */
    @PostMapping
    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequest request) {
        Loan loan = loanService.requestLoan(
                request.clientDocumentId(),
                request.loanType(),
                request.requestedAmount(),
                request.termMonths());
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    /** GET /api/loans/{id} — Consulta un préstamo por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    /** GET /api/loans/client/{documentId} — Préstamos de un cliente */
    @GetMapping("/client/{documentId}")
    public ResponseEntity<List<Loan>> getLoansByClient(@PathVariable String documentId) {
        return ResponseEntity.ok(loanService.getLoansByClient(documentId));
    }

    /** GET /api/loans/status/{status} — Filtrar préstamos por estado (uso de Analista) */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoansByStatus(status));
    }

    /** PATCH /api/loans/{id}/approve — Aprueba un préstamo (solo Analista Interno) */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Loan> approveLoan(@PathVariable Long id,
                                            @RequestBody ApproveRequest request) {
        return ResponseEntity.ok(loanService.approveLoan(id, request.approvedAmount(),
                request.interestRate(), request.analystUserId()));
    }

    /** PATCH /api/loans/{id}/reject — Rechaza un préstamo (solo Analista Interno) */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Loan> rejectLoan(@PathVariable Long id,
                                           @RequestParam Long analystUserId) {
        return ResponseEntity.ok(loanService.rejectLoan(id, analystUserId));
    }

    /** PATCH /api/loans/{id}/disburse — Desembolsa un préstamo aprobado */
    @PatchMapping("/{id}/disburse")
    public ResponseEntity<Loan> disburseLoan(@PathVariable Long id,
                                             @RequestParam Long analystUserId) {
        return ResponseEntity.ok(loanService.disburseLoan(id, analystUserId));
    }

    // ── Request DTOs ─────────────────────────────────────────────────────────
    public record LoanRequest(String clientDocumentId, LoanType loanType,
                              BigDecimal requestedAmount, int termMonths) {}

    public record ApproveRequest(BigDecimal approvedAmount, BigDecimal interestRate, Long analystUserId) {}
}