package com.audifarma.customer.domain.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("Cliente not found with id: " + id);
    }
}
