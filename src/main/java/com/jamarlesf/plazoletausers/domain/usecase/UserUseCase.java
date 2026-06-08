package com.jamarlesf.plazoletausers.domain.usecase;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.exception.RoleNotFoundException;
import com.jamarlesf.plazoletausers.domain.exception.UserEmailAlreadyExistsException;
import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.IRolePersistencePort;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;

import java.util.Optional;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void save(User user) {
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new UserEmailAlreadyExistsException();
        }
        Role role = rolePersistencePort.findById(
                user.getRole().getId()).orElseThrow(
                        () -> new RoleNotFoundException(user.getRole().getId()));

        user.setRole(role);
        userPersistencePort.save(user);
    }
}
