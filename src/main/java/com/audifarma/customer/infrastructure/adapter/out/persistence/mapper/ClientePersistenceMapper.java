package com.audifarma.customer.infrastructure.adapter.out.persistence.mapper;

import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.infrastructure.adapter.out.persistence.entity.ClienteJpaEntity;

import java.util.Collections;
import java.util.List;

public class ClientePersistenceMapper {

    private ClientePersistenceMapper() {}

    public static ClienteJpaEntity toEntity(Cliente cliente) {
        return new ClienteJpaEntity(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getNumeroDocumento(),
                cliente.getTipoDocumento(),
                cliente.getEdad(),
                cliente.getActivo()
        );
    }

    public static Cliente toDomain(ClienteJpaEntity entity) {
        return toDomain(entity, Collections.emptyList());
    }

    public static Cliente toDomain(ClienteJpaEntity entity, List<Direccion> direcciones) {
        return new Cliente(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getNumeroDocumento(),
                entity.getTipoDocumento(),
                entity.getEdad(),
                entity.getActivo(),
                direcciones
        );
    }

}
