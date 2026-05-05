package com.bank.application.controller;

import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.Client;
import com.bank.domain.service.ClientService;
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
    public ResponseEntity<Client> createClient(@RequestBody CreateClientRequest request) {
        Client client = new Client();
        client.setDocumentId(request.documentId());
        client.setName(request.name());
        client.setIdentification(request.identification());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setAddress(request.address());

        Client created = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Client> getClient(@PathVariable String documentId) {
        return ResponseEntity.ok(clientService.findByDocumentId(documentId));
    }

    @PatchMapping("/{documentId}/status")
    public ResponseEntity<Client> updateStatus(@PathVariable String documentId,
                                               @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(clientService.updateStatus(documentId, request.status()));
    }

    public record CreateClientRequest(String documentId,
                                      String name,
                                      String identification,
                                      String email,
                                      String phone,
                                      String address) {}

    public record UpdateStatusRequest(UserStatus status) {}
}
