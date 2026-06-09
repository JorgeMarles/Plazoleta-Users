package com.jamarlesf.plazoletausers.infrastructure.out.jpa.adapter;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.UserEntity;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserServicePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toUserList(userRepository.findAll());
    }
}
