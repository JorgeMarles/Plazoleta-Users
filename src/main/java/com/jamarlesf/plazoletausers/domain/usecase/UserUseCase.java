package com.jamarlesf.plazoletausers.domain.usecase;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import com.jamarlesf.plazoletausers.domain.exception.RoleNotFoundException;
import com.jamarlesf.plazoletausers.domain.exception.UserEmailAlreadyExistsException;
import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.IEncryptionPort;
import com.jamarlesf.plazoletausers.domain.spi.IRolePersistencePort;
import com.jamarlesf.plazoletausers.domain.spi.ITokenProviderPort;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IEncryptionPort encryptionPort;
    private final ITokenProviderPort tokenProviderPort;


    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IEncryptionPort encryptionPort, ITokenProviderPort tokenProviderPort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.encryptionPort = encryptionPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public void save(User user, String requestUserRole) {
        user.validate();
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new UserEmailAlreadyExistsException();
        }
        Role role = rolePersistencePort.findById(
                user.getRole().getId()).orElseThrow(
                () -> new RoleNotFoundException(user.getRole().getId()));

        if(Role.PROPIETARIO.equals(role.getName()) && !Role.ADMINISTRADOR.equals(requestUserRole)) {
            throw new DomainException("No tiene los permisos para realizar esta acción");
        }

        if(Role.EMPLEADO.equals(role.getName()) && !Role.PROPIETARIO.equals(requestUserRole)) {
            throw new DomainException("No tiene los permisos para realizar esta acción");
        }

        user.setRole(role);
        user.setPassword(encryptionPort.encryptPassword(user.getPassword()));
        userPersistencePort.save(user);
    }

    @Override
    public List<User> findAll() {
        return userPersistencePort.findAll();
    }

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id);
    }

    @Override
    public String login(String email, String password) {
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new DomainException("Correo o contraseña incorrectos"));

        if(!encryptionPort.matches(password, user.getPassword())) {
            throw new DomainException("Correo o contraseña incorrectos");
        }

        return tokenProviderPort.generateToken(user);
    }
}
