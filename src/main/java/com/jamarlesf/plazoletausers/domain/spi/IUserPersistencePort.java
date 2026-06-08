package com.jamarlesf.plazoletausers.domain.spi;

import com.jamarlesf.plazoletausers.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {
    void save(User user);

    boolean existsByEmail(String email);

    List<User> findAll();
}
