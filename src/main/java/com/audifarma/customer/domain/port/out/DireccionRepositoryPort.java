package com.audifarma.customer.domain.port.out;

import com.audifarma.customer.domain.model.Direccion;

import java.util.List;
import java.util.Optional;

public interface DireccionRepositoryPort {
    Optional<Direccion> findById(Long id);
    List<Direccion> findAllByClienteId(Long clienteId);
    Direccion save(Direccion direccion, Long clienteId);
    void deleteById(Long id);
    boolean existsByIdAndClienteId(Long id, Long clienteId);
}
