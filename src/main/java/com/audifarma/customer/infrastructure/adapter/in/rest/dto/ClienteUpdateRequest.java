package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteUpdateRequest(
        @NotBlank(message = "nombre is required")
        String nombre,

        @NotBlank(message = "apellido is required")
        String apellido,

        @NotBlank(message = "tipoDocumento is required")
        String tipoDocumento,

        @NotNull(message = "edad is required")
        @Min(value = 0, message = "edad must be non-negative")
        Integer edad,

        @NotNull(message = "activo is required")
        Boolean activo
) {}
