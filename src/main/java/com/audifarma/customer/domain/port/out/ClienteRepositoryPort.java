package com.audifarma.customer.domain.port.out;

import com.audifarma.customer.domain.model.Cliente;

import java.util.Optional;

public interface ClienteRepositoryPort {
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);
    boolean existsByNumeroDocumento(String numeroDocumento);
    Cliente save(Cliente cliente);
}
