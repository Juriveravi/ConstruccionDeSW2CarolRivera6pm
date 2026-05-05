package com.bank.domain.service;

import com.bank.domain.enums.UserStatus;
import com.bank.domain.model.Client;
import com.bank.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing client information.
 * Single Responsibility: Client CRUD operations.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Create a new client.
     *
     * @param client the client to create
     * @return the created client with ACTIVE status
     * @throws IllegalArgumentException if a client with the same document ID already exists
     */
    public Client createClient(Client client) {
        if (clientRepository.findByDocumentId(client.getDocumentId()).isPresent()) {
            throw new IllegalArgumentException("Client already exists: " + client.getDocumentId());
        }

        client.setStatus(UserStatus.ACTIVE);
        return clientRepository.save(client);
    }

    /**
     * Find a client by document ID.
     *
     * @param documentId the client's document ID
     * @return the client
     * @throws IllegalArgumentException if client not found
     */
    public Client findByDocumentId(String documentId) {
        return clientRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + documentId));
    }

    /**
     * Update the status of a client.
     *
     * @param documentId the client's document ID
     * @param status the new status
     * @return the updated client
     * @throws IllegalArgumentException if client not found
     */
    public Client updateStatus(String documentId, UserStatus status) {
        Client client = findByDocumentId(documentId);
        client.setStatus(status);
        return clientRepository.save(client);
    }
}