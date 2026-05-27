package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record DireccionRequest(
        @NotBlank(message = "departamento is required")
        String departamento,

        @NotBlank(message = "ciudad is required")
        String ciudad,

        @NotBlank(message = "direccionCompleta is required")
        String direccionCompleta
) {}
