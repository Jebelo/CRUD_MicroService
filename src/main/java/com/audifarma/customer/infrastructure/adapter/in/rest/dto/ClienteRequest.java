package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClienteRequest(
        @NotBlank(message = "nombre is required")
        String nombre,

        @NotBlank(message = "apellido is required")
        String apellido,

        @NotBlank(message = "numeroDocumento is required")
        String numeroDocumento,

        @NotBlank(message = "tipoDocumento is required")
        String tipoDocumento,

        @NotNull(message = "edad is required")
        @Min(value = 0, message = "edad must be non-negative")
        Integer edad,

        @NotNull(message = "activo is required")
        Boolean activo,

        @Valid
        List<DireccionRequest> direcciones
) {}
