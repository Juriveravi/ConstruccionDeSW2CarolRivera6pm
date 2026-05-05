package com.bank.application.controller;

import com.bank.application.dto.ClientResponse;
import com.bank.application.dto.CreateClientRequest;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.UpdateClientStatusRequest;
import com.bank.domain.model.Client;
import com.bank.domain.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
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

    @GetMapping("/{documentId}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String documentId) {
        return ResponseEntity.ok(DtoMapper.toClientResponse(clientService.findByDocumentId(documentId)));
    }

    @PatchMapping("/{documentId}/status")
    public ResponseEntity<ClientResponse> updateStatus(@PathVariable String documentId,
                                               @Valid @RequestBody UpdateClientStatusRequest request) {
        return ResponseEntity.ok(DtoMapper.toClientResponse(clientService.updateStatus(documentId, request.status())));
    }
}
