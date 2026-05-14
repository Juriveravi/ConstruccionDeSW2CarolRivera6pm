package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.service.CreateLoanService;
import com.bank.domain.service.ApproveLoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class GetLoanController {

    private final ApproveLoanService approveLoanService;

    public GetLoanController(ApproveLoanService approveLoanService) {
        this.approveLoanService = approveLoanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(DtoMapper.toLoanResponse(approveLoanService.getLoan(id)));
    }
}