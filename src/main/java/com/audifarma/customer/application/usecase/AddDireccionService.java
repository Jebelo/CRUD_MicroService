package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.in.AddDireccionUseCase;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class AddDireccionService implements AddDireccionUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final DireccionRepositoryPort direccionRepository;

    public AddDireccionService(ClienteRepositoryPort clienteRepository,
                               DireccionRepositoryPort direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    @Transactional
    public Direccion addDireccion(Long clienteId, Direccion direccion) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(clienteId));

        return direccionRepository.save(direccion, clienteId);
    }
}
