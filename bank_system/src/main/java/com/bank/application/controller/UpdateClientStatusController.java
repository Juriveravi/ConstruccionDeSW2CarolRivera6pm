package com.bank.application.controller;

import com.bank.application.dto.ClientResponse;
import com.bank.application.dto.DtoMapper;
import com.bank.application.dto.UpdateClientStatusRequest;
import com.bank.domain.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class UpdateClientStatusController {

    private final ClientService clientService;

    public UpdateClientStatusController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PatchMapping("/{documentId}/status")
    public ResponseEntity<ClientResponse> updateStatus(@PathVariable String documentId,
                                                       @Valid @RequestBody UpdateClientStatusRequest request) {
        return ResponseEntity.ok(DtoMapper.toClientResponse(clientService.updateStatus(documentId, request.status())));
    }
}