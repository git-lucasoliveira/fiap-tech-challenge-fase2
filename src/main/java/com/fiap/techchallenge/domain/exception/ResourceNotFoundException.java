package com.fiap.techchallenge.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " com id " + id + " não encontrado.");
    }
}

