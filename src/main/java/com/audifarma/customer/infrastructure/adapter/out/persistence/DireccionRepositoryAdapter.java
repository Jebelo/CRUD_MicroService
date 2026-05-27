package com.audifarma.customer.infrastructure.adapter.out.persistence;

import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.domain.port.out.DireccionRepositoryPort;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.ClienteJpaEntity;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.DireccionJpaEntity;
import com.audifarma.customer.infrastructure.adapter.out.persistence.mapper.DireccionPersistenceMapper;
import com.audifarma.customer.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import com.audifarma.customer.infrastructure.adapter.out.persistence.repository.DireccionJpaRepository;

import java.util.List;
import java.util.Optional;

public class DireccionRepositoryAdapter implements DireccionRepositoryPort {

    private final DireccionJpaRepository jpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;

    public DireccionRepositoryAdapter(DireccionJpaRepository jpaRepository,
                                      ClienteJpaRepository clienteJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Optional<Direccion> findById(Long id) {
        return jpaRepository.findById(id)
                .map(DireccionPersistenceMapper::toDomain);
    }

    @Override
    public List<Direccion> findAllByClienteId(Long clienteId) {
        return jpaRepository.findAllByClienteId(clienteId).stream()
                .map(DireccionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Direccion save(Direccion direccion, Long clienteId) {
        ClienteJpaEntity clienteEntity = clienteJpaRepository.getReferenceById(clienteId);
        DireccionJpaEntity entity = DireccionPersistenceMapper.toEntity(direccion, clienteEntity);
        DireccionJpaEntity saved = jpaRepository.save(entity);
        return DireccionPersistenceMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdAndClienteId(Long id, Long clienteId) {
        return jpaRepository.existsByIdAndClienteId(id, clienteId);
    }
}
