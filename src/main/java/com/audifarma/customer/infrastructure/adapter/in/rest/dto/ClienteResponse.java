package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record ClienteResponse(
        Long id,
        String nombre,
        String apellido,
        String numeroDocumento,
        String tipoDocumento,
        Integer edad,
        Boolean activo,
        List<DireccionResponse> direcciones
) {}
