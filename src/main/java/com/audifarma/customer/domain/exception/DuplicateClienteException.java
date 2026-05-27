package com.audifarma.customer.domain.exception;

public class DuplicateClienteException extends RuntimeException {

    public DuplicateClienteException(String numeroDocumento) {
        super("Cliente already exists with document number: " + numeroDocumento);
    }
}
