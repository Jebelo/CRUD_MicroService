package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.in.GetClienteUseCase;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;

import java.util.List;

public class GetClienteService implements GetClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final DireccionRepositoryPort direccionRepository;

    public GetClienteService(ClienteRepositoryPort clienteRepository,
                             DireccionRepositoryPort direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    public Cliente getById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        List<Direccion> direcciones = direccionRepository.findAllByClienteId(id);
        return cliente.withDirecciones(direcciones);
    }
}
