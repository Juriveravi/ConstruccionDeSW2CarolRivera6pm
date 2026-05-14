package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.enums.LoanStatus;
import com.bank.domain.service.CreateLoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class GetLoansByStatusController {

    private final CreateLoanService createLoanService;

    public GetLoansByStatusController(CreateLoanService createLoanService) {
        this.createLoanService = createLoanService;
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoanResponse>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(createLoanService.getLoansByStatus(status)
                .stream()
                .map(DtoMapper::toLoanResponse)
                .toList());
    }
}