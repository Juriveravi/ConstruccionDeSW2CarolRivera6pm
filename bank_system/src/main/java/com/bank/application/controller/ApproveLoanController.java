package com.bank.application.controller;

import com.bank.application.dto.ApproveLoanRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.model.Loan;
import com.bank.domain.service.ApproveLoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class ApproveLoanController {

    private final ApproveLoanService approveLoanService;

    public ApproveLoanController(ApproveLoanService approveLoanService) {
        this.approveLoanService = approveLoanService;
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable Long id,
                                                    @Valid @RequestBody ApproveLoanRequest request) {
        Loan loan = approveLoanService.approveLoan(id, request.approvedAmount(), request.interestRate());
        return ResponseEntity.ok(DtoMapper.toLoanResponse(loan));
    }
}