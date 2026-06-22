package com.jamarlesf.plazoletausers.domain.spi;

import com.jamarlesf.plazoletausers.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserPersistencePort {
    void save(User user);

    boolean existsByEmail(String email);

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByEmail(String email);
}
