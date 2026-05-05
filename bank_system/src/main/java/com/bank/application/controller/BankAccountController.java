package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.OpenAccountRequest;
import com.bank.domain.model.BankAccount;
import com.bank.domain.service.CreateAccountService;
import com.bank.domain.service.FindAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final CreateAccountService createAccountService;
    private final FindAccountService findAccountService;

    public BankAccountController(CreateAccountService createAccountService,
                                 FindAccountService findAccountService) {
        this.createAccountService = createAccountService;
        this.findAccountService = findAccountService;
    }

    /** POST /api/accounts — Abre una nueva cuenta bancaria */
    @PostMapping
    public ResponseEntity<BankAccountResponse> openAccount(@Valid @RequestBody OpenAccountRequest request) {
        BankAccount account = createAccountService.openAccount(
                request.clientDocumentId(),
                request.accountType(),
                request.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toBankAccountResponse(account));
    }

    /** GET /api/accounts/{accountNumber} — Consulta una cuenta por número */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(findAccountService.findByAccountNumber(accountNumber)));
    }

    /** GET /api/accounts/owner/{documentId} — Cuentas de un cliente */
    @GetMapping("/owner/{documentId}")
    public ResponseEntity<List<BankAccountResponse>> getAccountsByOwner(@PathVariable String documentId) {
        return ResponseEntity.ok(findAccountService.findByOwner(documentId)
                .stream()
                .map(DtoMapper::toBankAccountResponse)
                .toList());
    }
}