package com.bank.domain.port;

import com.bank.domain.model.Client;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findByDocumentId(String documentId);
    boolean existsByDocumentId(String documentId);
    boolean existsByEmail(String email);
}