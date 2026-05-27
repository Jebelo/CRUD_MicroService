package com.audifarma.customer.infrastructure.adapter.in.rest.dto;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}
