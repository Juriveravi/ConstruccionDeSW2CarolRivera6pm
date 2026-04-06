package com.bank.infrastructure.persistence.jpa;

import com.bank.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDocumentId(String documentId);
    boolean existsByDocumentId(String documentId);
    boolean existsByEmail(String email);
}