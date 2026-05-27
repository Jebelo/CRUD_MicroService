package com.audifarma.customer.application.usecase;

import com.audifarma.customer.domain.exception.DuplicateClienteException;
import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.in.CreateClienteUseCase;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class CreateClienteService implements CreateClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final DireccionRepositoryPort direccionRepository;

    public CreateClienteService(ClienteRepositoryPort clienteRepository,
                                DireccionRepositoryPort direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    @Transactional
    public Cliente create(Cliente cliente) {
        if (clienteRepository.existsByNumeroDocumento(cliente.getNumeroDocumento())) {
            throw new DuplicateClienteException(cliente.getNumeroDocumento());
        }

        // Save cliente without direcciones first to obtain the generated id
        Cliente savedCliente = clienteRepository.save(cliente.withDirecciones(List.of()));

        // Persist each provided direccion linked to the new cliente id
        List<Direccion> savedDirecciones = new ArrayList<>();
        for (Direccion direccion : cliente.getDirecciones()) {
            savedDirecciones.add(direccionRepository.save(direccion, savedCliente.getId()));
        }

        return savedCliente.withDirecciones(savedDirecciones);
    }
}
