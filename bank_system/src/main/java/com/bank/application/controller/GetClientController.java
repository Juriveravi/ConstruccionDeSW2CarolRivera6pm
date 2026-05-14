package com.bank.application.controller;

import com.bank.application.dto.ClientResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.domain.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class GetClientController {

    private final ClientService clientService;

    public GetClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String documentId) {
        return ResponseEntity.ok(DtoMapper.toClientResponse(clientService.findByDocumentId(documentId)));
    }
}