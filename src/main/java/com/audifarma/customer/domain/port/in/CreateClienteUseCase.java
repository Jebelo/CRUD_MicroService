package com.audifarma.customer.domain.port.in;

import com.audifarma.customer.domain.model.Cliente;

public interface CreateClienteUseCase {
    Cliente create(Cliente cliente);
}
