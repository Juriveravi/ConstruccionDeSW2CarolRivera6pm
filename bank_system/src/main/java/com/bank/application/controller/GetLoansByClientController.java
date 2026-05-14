package com.bank.application.controller;

import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.LoanResponse;
import com.bank.domain.service.CreateLoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class GetLoansByClientController {

    private final CreateLoanService createLoanService;

    public GetLoansByClientController(CreateLoanService createLoanService) {
        this.createLoanService = createLoanService;
    }

    @GetMapping("/client/{documentId}")
    public ResponseEntity<List<LoanResponse>> getLoansByClient(@PathVariable String documentId) {
        return ResponseEntity.ok(createLoanService.getLoansByClient(documentId)
                .stream()
                .map(DtoMapper::toLoanResponse)
                .toList());
    }
}