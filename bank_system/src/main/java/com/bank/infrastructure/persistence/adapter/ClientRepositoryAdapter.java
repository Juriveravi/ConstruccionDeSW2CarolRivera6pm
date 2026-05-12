package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.Client;
import com.bank.domain.port.ClientRepository;
import com.bank.infrastructure.persistence.entities.ClientEntity;
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
        ClientEntity entity = toEntity(client);
        ClientEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Client> findByDocumentId(String documentId) {
        return jpa.findByDocumentId(documentId).map(this::toDomain);
    }

    @Override
    public boolean existsByDocumentId(String documentId) {
        return jpa.existsByDocumentId(documentId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    private ClientEntity toEntity(Client client) {
        ClientEntity e = new ClientEntity();
        e.setId(client.getId());
        e.setDocumentId(client.getDocumentId());
        e.setName(client.getName());
        e.setIdentification(client.getIdentification());
        e.setEmail(client.getEmail());
        e.setPhone(client.getPhone());
        e.setAddress(client.getAddress());
        e.setStatus(client.getStatus());
        return e;
    }

    private Client toDomain(ClientEntity e) {
        Client c = new Client();
        c.setId(e.getId());
        c.setDocumentId(e.getDocumentId());
        c.setName(e.getName());
        c.setIdentification(e.getIdentification());
        c.setEmail(e.getEmail());
        c.setPhone(e.getPhone());
        c.setAddress(e.getAddress());
        c.setStatus(e.getStatus());
        return c;
    }
}