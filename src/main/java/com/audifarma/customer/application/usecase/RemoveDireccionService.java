package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.exception.DireccionNotFoundException;
import com.audifarma.customer.domain.port.in.RemoveDireccionUseCase;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class RemoveDireccionService implements RemoveDireccionUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final DireccionRepositoryPort direccionRepository;

    public RemoveDireccionService(ClienteRepositoryPort clienteRepository,
                                  DireccionRepositoryPort direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    @Transactional
    public void removeDireccion(Long clienteId, Long direccionId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException(clienteId));

        if (!direccionRepository.existsByIdAndClienteId(direccionId, clienteId)) {
            throw new DireccionNotFoundException(direccionId, clienteId);
        }

        direccionRepository.deleteById(direccionId);
    }
}
