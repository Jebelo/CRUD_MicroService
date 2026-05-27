package com.audifarma.customer.domain.port.in;

public interface RemoveDireccionUseCase {
    void removeDireccion(Long clienteId, Long direccionId);
}
