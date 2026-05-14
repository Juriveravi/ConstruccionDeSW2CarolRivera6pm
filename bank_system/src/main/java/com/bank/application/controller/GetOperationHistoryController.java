package com.bank.application.controller;

import com.bank.domain.model.OperationLog;
import com.bank.domain.service.FindCustomerHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class GetOperationHistoryController {

    private final FindCustomerHistoryService findCustomerHistoryService;

    public GetOperationHistoryController(FindCustomerHistoryService findCustomerHistoryService) {
        this.findCustomerHistoryService = findCustomerHistoryService;
    }

    @GetMapping("/history/{productId}")
    public ResponseEntity<List<OperationLog>> getHistory(@PathVariable String productId) {
        return ResponseEntity.ok(findCustomerHistoryService.getCustomerHistory(productId));
    }
}