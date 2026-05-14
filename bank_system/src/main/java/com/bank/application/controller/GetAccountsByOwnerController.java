package com.bank.application.controller;

import com.bank.application.dto.BankAccountResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.domain.service.FindAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class GetAccountsByOwnerController {

    private final FindAccountService findAccountService;

    public GetAccountsByOwnerController(FindAccountService findAccountService) {
        this.findAccountService = findAccountService;
    }

    @GetMapping("/owner/{documentId}")
    public ResponseEntity<List<BankAccountResponse>> getAccountsByOwner(@PathVariable String documentId) {
        return ResponseEntity.ok(findAccountService.findByOwner(documentId)
                .stream()
                .map(DtoMapper::toBankAccountResponse)
                .toList());
    }
}