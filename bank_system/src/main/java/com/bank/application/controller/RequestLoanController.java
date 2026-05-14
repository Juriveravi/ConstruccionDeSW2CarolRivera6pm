package com.bank.application.controller;

import com.bank.application.dto.CreateLoanRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.model.Loan;
import com.bank.domain.service.CreateLoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class RequestLoanController {

    private final CreateLoanService createLoanService;

    public RequestLoanController(CreateLoanService createLoanService) {
        this.createLoanService = createLoanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> requestLoan(@Valid @RequestBody CreateLoanRequest request) {
        Loan loan = createLoanService.requestLoan(
                request.clientDocumentId(),
                request.loanType(),
                request.requestedAmount(),
                request.termMonths());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toLoanResponse(loan));
    }
}