package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.MoneyOperationRequest;
import com.bank.application.dto.OpenAccountRequest;
import com.bank.domain.model.BankAccount;
import com.bank.domain.service.CreateAccountService;
import com.bank.domain.service.DepositMoneyService;
import com.bank.domain.service.FindAccountService;
import com.bank.domain.service.WithdrawMoneyService;
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
    private final DepositMoneyService depositMoneyService;
    private final WithdrawMoneyService withdrawMoneyService;

    public BankAccountController(CreateAccountService createAccountService,
                                 FindAccountService findAccountService,
                                 DepositMoneyService depositMoneyService,
                                 WithdrawMoneyService withdrawMoneyService) {
        this.createAccountService = createAccountService;
        this.findAccountService = findAccountService;
        this.depositMoneyService = depositMoneyService;
        this.withdrawMoneyService = withdrawMoneyService;
    }

    /** POST /api/accounts — Open a new bank account */
    @PostMapping
    public ResponseEntity<BankAccountResponse> openAccount(@Valid @RequestBody OpenAccountRequest request) {
        BankAccount account = createAccountService.openAccount(
                request.clientDocumentId(),
                request.accountType(),
                request.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toBankAccountResponse(account));
    }

    /** GET /api/accounts/{accountNumber} — Get account by number */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(
                findAccountService.findByAccountNumber(accountNumber)));
    }

    /** GET /api/accounts/owner/{documentId} — Get accounts by owner */
    @GetMapping("/owner/{documentId}")
    public ResponseEntity<List<BankAccountResponse>> getAccountsByOwner(@PathVariable String documentId) {
        return ResponseEntity.ok(findAccountService.findByOwner(documentId)
                .stream()
                .map(DtoMapper::toBankAccountResponse)
                .toList());
    }

    /** POST /api/accounts/{accountNumber}/deposit — Deposit money */
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<BankAccountResponse> deposit(@PathVariable String accountNumber,
                                                       @Valid @RequestBody MoneyOperationRequest request) {
        BankAccount account = depositMoneyService.depositMoney(accountNumber, request.amount());
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(account));
    }

    /** POST /api/accounts/{accountNumber}/withdraw — Withdraw money */
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<BankAccountResponse> withdraw(@PathVariable String accountNumber,
                                                        @Valid @RequestBody MoneyOperationRequest request) {
        BankAccount account = withdrawMoneyService.withdrawMoney(accountNumber, request.amount());
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(account));
    }
}