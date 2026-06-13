package com.jamarlesf.plazoletausers.infrastructure.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuario con id " + id + " no encontrado");
    }
}
