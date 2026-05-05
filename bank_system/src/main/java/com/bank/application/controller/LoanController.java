package com.bank.application.controller;

import com.bank.application.dto.ApproveLoanRequest;
import com.bank.application.dto.CreateLoanRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.enums.LoanStatus;
import com.bank.domain.model.Loan;
import com.bank.domain.service.CreateLoanService;
import com.bank.domain.service.ApproveLoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final CreateLoanService createLoanService;
    private final ApproveLoanService approveLoanService;

    public LoanController(CreateLoanService createLoanService,
                         ApproveLoanService approveLoanService) {
        this.createLoanService = createLoanService;
        this.approveLoanService = approveLoanService;
    }

    /** POST /api/loans — Solicita un nuevo préstamo */
    @PostMapping
    public ResponseEntity<LoanResponse> requestLoan(@Valid @RequestBody CreateLoanRequest request) {
        Loan loan = createLoanService.requestLoan(
                request.clientDocumentId(),
                request.loanType(),
                request.requestedAmount(),
                request.termMonths());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toLoanResponse(loan));
    }

    /** GET /api/loans/{id} — Consulta un préstamo por ID */
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toLoanResponse(createLoanService.getLoan(id)));
    }

    /** GET /api/loans/client/{documentId} — Préstamos de un cliente */
    @GetMapping("/client/{documentId}")
    public ResponseEntity<List<LoanResponse>> getLoansByClient(@PathVariable String documentId) {
        return ResponseEntity.ok(createLoanService.getLoansByClient(documentId)
                .stream()
                .map(DtoMapper::toLoanResponse)
                .toList());
    }

    /** GET /api/loans/status/{status} — Filtrar préstamos por estado (uso de Analista) */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanResponse>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(createLoanService.getLoansByStatus(status)
                .stream()
                .map(DtoMapper::toLoanResponse)
                .toList());
    }

    /** PATCH /api/loans/{id}/approve — Aprueba un préstamo (solo Analista Interno) */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable Long id,
                                                    @Valid @RequestBody ApproveLoanRequest request) {
        return ResponseEntity.ok(DtoMapper.toLoanResponse(approveLoanService.approveLoan(id, request.approvedAmount(),
                request.interestRate())));
    }

    /** PATCH /api/loans/{id}/reject — Rechaza un préstamo (solo Analista Interno) */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LoanResponse> rejectLoan(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toLoanResponse(approveLoanService.rejectLoan(id)));
    }

    /** PATCH /api/loans/{id}/disburse — Desembolsa un préstamo aprobado */
    @PatchMapping("/{id}/disburse")
    public ResponseEntity<LoanResponse> disburseLoan(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toLoanResponse(approveLoanService.disburseLoan(id)));
    }
}