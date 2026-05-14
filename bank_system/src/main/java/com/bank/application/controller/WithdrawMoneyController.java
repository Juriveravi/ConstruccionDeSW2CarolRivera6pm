package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.MoneyOperationRequest;
import com.bank.domain.model.BankAccount;
import com.bank.domain.service.WithdrawMoneyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class WithdrawMoneyController {

    private final WithdrawMoneyService withdrawMoneyService;

    public WithdrawMoneyController(WithdrawMoneyService withdrawMoneyService) {
        this.withdrawMoneyService = withdrawMoneyService;
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<BankAccountResponse> withdraw(@PathVariable String accountNumber,
                                                        @Valid @RequestBody MoneyOperationRequest request) {
        BankAccount account = withdrawMoneyService.withdrawMoney(accountNumber, request.amount());
        return ResponseEntity.ok(DtoMapper.toBankAccountResponse(account));
    }
}