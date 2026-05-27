package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.ClienteNotFoundException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.port.in.UpdateClienteUseCase;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class UpdateClienteService implements UpdateClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;

    public UpdateClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente update(Long id, Cliente updatedData) {
        Cliente existing = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        Cliente merged = existing.updateData(
                updatedData.getNombre(),
                updatedData.getApellido(),
                updatedData.getTipoDocumento(),
                updatedData.getEdad(),
                updatedData.getActivo()
        );

        return clienteRepository.save(merged);
    }
}
