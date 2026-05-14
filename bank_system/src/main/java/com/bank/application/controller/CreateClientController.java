package com.bank.application.controller;

import com.bank.application.dto.ClientResponse;
import com.bank.application.dto.CreateClientRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.domain.model.Client;
import com.bank.domain.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class CreateClientController {

    private final ClientService clientService;

    public CreateClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        Client client = new Client();
        client.setDocumentId(request.documentId());
        client.setName(request.name());
        client.setIdentification(request.identification());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setAddress(request.address());

        Client created = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.toClientResponse(created));
    }
}