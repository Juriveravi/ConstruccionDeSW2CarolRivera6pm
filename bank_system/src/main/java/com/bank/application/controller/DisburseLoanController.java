package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.model.Loan;
import com.bank.domain.service.ApproveLoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class DisburseLoanController {

    private final ApproveLoanService approveLoanService;

    public DisburseLoanController(ApproveLoanService approveLoanService) {
        this.approveLoanService = approveLoanService;
    }

    @PatchMapping("/{id}/disburse")
    public ResponseEntity<LoanResponse> disburseLoan(@PathVariable Long id) {
        Loan loan = approveLoanService.disburseLoan(id);
        return ResponseEntity.ok(DtoMapper.toLoanResponse(loan));
    }
}