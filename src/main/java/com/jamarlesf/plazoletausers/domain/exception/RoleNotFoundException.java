package com.jamarlesf.plazoletausers.domain.exception;

public class RoleNotFoundException extends DomainException {
    public RoleNotFoundException(Long id) {
        super("No existe el rol con id "+id);
    }
}
