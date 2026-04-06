package com.bank.domain.service;

import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.Client;
import com.bank.domain.model.OperationLog;
import com.bank.domain.port.ClientRepository;
import com.bank.domain.port.OperationLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final OperationLogRepository operationLogRepository;

    public ClientService(ClientRepository clientRepository,
                         OperationLogRepository operationLogRepository) {
        this.clientRepository = clientRepository;
        this.operationLogRepository = operationLogRepository;
    }

    public Client createClient(Client client, Long userId) {

        if (clientRepository.findByDocumentId(client.getDocumentId()).isPresent()) {
            throw new IllegalArgumentException("Client already exists: " + client.getDocumentId());
        }

        client.setStatus(UserStatus.ACTIVE);

        Client saved = clientRepository.save(client);

        operationLogRepository.save(buildLog(
                "CREACION_CLIENTE",
                userId,
                "SISTEMA",
                client.getDocumentId(),
                Map.of("estado", "ACTIVO")
        ));

        return saved;
    }

    public Client findByDocumentId(String documentId) {
        return clientRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + documentId));
    }

    public Client updateStatus(String documentId, UserStatus status, Long userId) {
        Client client = findByDocumentId(documentId);

        UserStatus previous = client.getStatus();
        client.setStatus(status);

        operationLogRepository.save(buildLog(
                "CAMBIO_ESTADO_CLIENTE",
                userId,
                "SISTEMA",
                documentId,
                Map.of(
                        "estadoAnterior", previous,
                        "nuevoEstado", status
                )
        ));

        return clientRepository.save(client);
    }

    private OperationLog buildLog(String type, Long userId, String role,
                                 String productId, Map<String, Object> details) {
        return new OperationLog(null, type, LocalDateTime.now(), userId, role, productId, details);
    }
}