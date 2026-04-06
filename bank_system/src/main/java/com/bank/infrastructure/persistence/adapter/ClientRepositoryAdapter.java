package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.Client;
import com.bank.domain.port.ClientRepository;
import com.bank.infrastructure.persistence.jpa.JpaClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final JpaClientRepository jpa;

    public ClientRepositoryAdapter(JpaClientRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Client save(Client client) {
        return jpa.save(client);
    }

    @Override
    public Optional<Client> findByDocumentId(String documentId) {
        return jpa.findByDocumentId(documentId);
    }

    @Override
    public boolean existsByDocumentId(String documentId) {
        return jpa.existsByDocumentId(documentId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }
}