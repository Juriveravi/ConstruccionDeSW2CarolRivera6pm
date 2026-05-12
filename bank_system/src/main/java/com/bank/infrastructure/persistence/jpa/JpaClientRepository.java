package com.bank.infrastructure.persistence.jpa;

import com.bank.infrastructure.persistence.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByDocumentId(String documentId);
    boolean existsByDocumentId(String documentId);
    boolean existsByEmail(String email);
}