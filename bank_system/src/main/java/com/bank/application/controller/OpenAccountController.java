package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.OpenAccountRequest;
import com.bank.domain.model.BankAccount;
import com.bank.domain.service.CreateAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class OpenAccountController {

    private final CreateAccountService createAccountService;

    public OpenAccountController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    @PostMapping
    public ResponseEntity<BankAccountResponse> openAccount(@Valid @RequestBody OpenAccountRequest request) {
        BankAccount account = createAccountService.openAccount(
                request.clientDocumentId(),
                request.accountType(),
                request.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toBankAccountResponse(account));
    }
}