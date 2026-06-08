package com.jamarlesf.plazoletausers.domain.spi;

import com.jamarlesf.plazoletausers.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {
    void save(Role role);
    Optional<Role> findById(Long id);
}
