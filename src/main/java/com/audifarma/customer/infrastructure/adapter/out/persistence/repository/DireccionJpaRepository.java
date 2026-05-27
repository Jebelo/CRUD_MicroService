package com.audifarma.customer.infrastructure.adapter.out.persistence.repository;

import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.DireccionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionJpaRepository extends JpaRepository<DireccionJpaEntity, Long> {
    List<DireccionJpaEntity> findAllByClienteId(Long clienteId);
    boolean existsByIdAndClienteId(Long id, Long clienteId);
}
