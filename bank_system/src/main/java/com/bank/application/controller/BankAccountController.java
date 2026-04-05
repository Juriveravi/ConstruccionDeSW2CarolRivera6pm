package com.bank.application.controller;

import com.bank.domain.enums.AccountType;
import com.bank.domain.enums.Currency;
import com.bank.domain.model.BankAccount;
import com.bank.domain.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /** POST /api/accounts — Abre una nueva cuenta bancaria */
    @PostMapping
    public ResponseEntity<BankAccount> openAccount(@RequestBody OpenAccountRequest request) {
        BankAccount account = bankAccountService.openAccount(
                request.clientDocumentId(),
                request.accountType(),
                request.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /** GET /api/accounts/{accountNumber} — Consulta una cuenta por número */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankAccountService.findByAccountNumber(accountNumber));
    }

    /** GET /api/accounts/owner/{documentId} — Cuentas de un cliente */
    @GetMapping("/owner/{documentId}")
    public ResponseEntity<List<BankAccount>> getAccountsByOwner(@PathVariable String documentId) {
        return ResponseEntity.ok(bankAccountService.findByOwner(documentId));
    }

    // ── Request DTO ──────────────────────────────────────────────────────────
    public record OpenAccountRequest(String clientDocumentId, AccountType accountType, Currency currency) {}
}