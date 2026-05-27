package com.audifarma.customer.domain.port.in;

import com.audifarma.customer.domain.model.Direccion;

public interface AddDireccionUseCase {
    Direccion addDireccion(Long clienteId, Direccion direccion);
}
