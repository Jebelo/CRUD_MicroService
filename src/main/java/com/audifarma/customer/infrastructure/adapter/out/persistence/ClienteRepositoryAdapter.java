package com.audifarma.customer.infrastructure.adapter.out.persistence;

import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.port.out.ClienteRepositoryPort;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.ClienteJpaEntity;
import com.audifarma.customer.infrastructure.adapter.out.persistence.mapper.ClientePersistenceMapper;
import com.audifarma.customer.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;

import java.util.Optional;

public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository jpaRepository;

    public ClienteRepositoryAdapter(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ClientePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Cliente> findByNumeroDocumento(String numeroDocumento) {
        return jpaRepository.findByNumeroDocumento(numeroDocumento)
                .map(ClientePersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByNumeroDocumento(String numeroDocumento) {
        return jpaRepository.existsByNumeroDocumento(numeroDocumento);
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() != null && jpaRepository.existsById(cliente.getId())) {
            // Update: mutate only scalar fields to avoid orphaning direcciones
            ClienteJpaEntity existing = jpaRepository.findById(cliente.getId()).orElseThrow();
            existing.setNombre(cliente.getNombre());
            existing.setApellido(cliente.getApellido());
            existing.setTipoDocumento(cliente.getTipoDocumento());
            existing.setEdad(cliente.getEdad());
            existing.setActivo(cliente.getActivo());
            return ClientePersistenceMapper.toDomain(jpaRepository.save(existing));
        }
        // Create: build new entity (id is null → auto-generated)
        ClienteJpaEntity entity = ClientePersistenceMapper.toEntity(cliente);
        ClienteJpaEntity saved = jpaRepository.save(entity);
        return ClientePersistenceMapper.toDomain(saved);
    }
}
