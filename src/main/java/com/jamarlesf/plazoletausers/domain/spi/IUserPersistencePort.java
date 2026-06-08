package com.jamarlesf.plazoletausers.domain.spi;

import com.jamarlesf.plazoletausers.domain.model.User;

public interface IUserPersistencePort {
    void save(User user);
    boolean existsByEmail(String email);
}
