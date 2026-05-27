package com.audifarma.customer.infrastructure.adapter.in.rest.mapper;

import com.audifarma.customer.domain.model.Cliente;
import com.audifarma.customer.domain.model.Direccion;
import com.audifarma.customer.infrastructure.adapter.in.rest.dto.*;

import java.util.Collections;
import java.util.List;

public class ClienteRestMapper {

    private ClienteRestMapper() {}

    public static Cliente toDomain(ClienteRequest request) {
        List<Direccion> direcciones = request.direcciones() == null
                ? Collections.emptyList()
                : request.direcciones().stream()
                        .map(ClienteRestMapper::toDomain)
                        .toList();

        return new Cliente(null,
                request.nombre(),
                request.apellido(),
                request.numeroDocumento(),
                request.tipoDocumento(),
                request.edad(),
                request.activo(),
                direcciones);
    }

    public static Cliente toDomain(Long id, ClienteUpdateRequest request) {
        return new Cliente(id,
                request.nombre(),
                request.apellido(),
                null, // numeroDocumento is immutable — not passed in updates
                request.tipoDocumento(),
                request.edad(),
                request.activo(),
                Collections.emptyList());
    }

    public static Direccion toDomain(DireccionRequest request) {
        return new Direccion(null,
                request.departamento(),
                request.ciudad(),
                request.direccionCompleta());
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        List<DireccionResponse> direcciones = cliente.getDirecciones().stream()
                .map(ClienteRestMapper::toResponse)
                .toList();

        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getNumeroDocumento(),
                cliente.getTipoDocumento(),
                cliente.getEdad(),
                cliente.getActivo(),
                direcciones);
    }

    public static DireccionResponse toResponse(Direccion direccion) {
        return new DireccionResponse(
                direccion.getId(),
                direccion.getDepartamento(),
                direccion.getCiudad(),
                direccion.getDireccionCompleta());
    }
}
