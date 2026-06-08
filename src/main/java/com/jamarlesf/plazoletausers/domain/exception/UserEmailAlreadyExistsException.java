package com.jamarlesf.plazoletausers.domain.exception;

public class UserEmailAlreadyExistsException extends DomainException {
    public UserEmailAlreadyExistsException() {
        super("Ya existe un usuario con ese correo");
    }
}
