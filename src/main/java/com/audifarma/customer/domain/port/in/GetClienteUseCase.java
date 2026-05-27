package com.audifarma.customer.domain.port.in;

import com.audifarma.customer.domain.model.Cliente;

public interface GetClienteUseCase {
    Cliente getById(Long id);
}
