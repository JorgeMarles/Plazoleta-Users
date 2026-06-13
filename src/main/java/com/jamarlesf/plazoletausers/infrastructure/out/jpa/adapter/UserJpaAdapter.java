package com.jamarlesf.plazoletausers.infrastructure.out.jpa.adapter;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;
import com.jamarlesf.plazoletausers.infrastructure.exception.UserNotFoundException;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.UserEntity;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toUserList(userRepository.findAll());
    }

    @Override
    public User findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()) {
            return userEntityMapper.toUser(userEntity.get());
        }
        throw new UserNotFoundException(id);
    }
}
