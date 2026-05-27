package com.audifarma.customer.domain.port.in;

import com.audifarma.customer.domain.model.Cliente;

public interface UpdateClienteUseCase {
    Cliente update(Long id, Cliente updatedData);
}
