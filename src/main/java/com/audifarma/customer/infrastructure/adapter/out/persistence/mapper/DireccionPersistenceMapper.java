package com.audifarma.customer.infrastructure.adapter.out.persistence.mapper;

import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.ClienteJpaEntity;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.DireccionJpaEntity;

public class DireccionPersistenceMapper {

    private DireccionPersistenceMapper() {}

    public static DireccionJpaEntity toEntity(Direccion direccion, ClienteJpaEntity clienteEntity) {
        return new DireccionJpaEntity(
                direccion.getId(),
                direccion.getDepartamento(),
                direccion.getCiudad(),
                direccion.getDireccionCompleta(),
                clienteEntity
        );
    }

    public static Direccion toDomain(DireccionJpaEntity entity) {
        return new Direccion(
                entity.getId(),
                entity.getDepartamento(),
                entity.getCiudad(),
                entity.getDireccionCompleta()
        );
    }
}
