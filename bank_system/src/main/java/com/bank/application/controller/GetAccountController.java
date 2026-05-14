package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.domain.service.FindAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class GetAccountController {

    private final FindAccountService findAccountService;

    public GetAccountController(FindAccountService findAccountService) {
        this.findAccountService = findAccountService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(
                findAccountService.findByAccountNumber(accountNumber)));
    }
}