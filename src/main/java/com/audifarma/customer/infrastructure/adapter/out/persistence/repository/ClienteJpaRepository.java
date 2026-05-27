package com.audifarma.customer.infrastructure.adapter.out.persistence.repository;

import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.ClienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {
    Optional<ClienteJpaEntity> findByNumeroDocumento(String numeroDocumento);
    boolean existsByNumeroDocumento(String numeroDocumento);
}
