package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

public record DireccionResponse(
        Long id,
        String departamento,
        String ciudad,
        String direccionCompleta
) {}
