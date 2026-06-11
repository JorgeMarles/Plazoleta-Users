package com.jamarlesf.plazoletausers.domain.api;

import com.jamarlesf.plazoletausers.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserServicePort {
    void save(User user);
    List<User> findAll();
}
