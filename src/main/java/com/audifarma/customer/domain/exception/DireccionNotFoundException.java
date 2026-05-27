package com.audifarma.customer.domain.exception;

public class DireccionNotFoundException extends RuntimeException {

    public DireccionNotFoundException(Long direccionId, Long clienteId) {
        super("Direccion " + direccionId + " not found for cliente " + clienteId);
    }
}
