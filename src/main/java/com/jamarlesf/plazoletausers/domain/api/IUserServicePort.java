package com.jamarlesf.plazoletausers.domain.api;

import com.jamarlesf.plazoletausers.domain.model.User;

import java.util.List;

public interface IUserServicePort {
    void save(User user, String requestUserRole);
    void registerClient(User user);
    List<User> findAll();
    User findById(Long id);
    String login(String email, String password);
}
