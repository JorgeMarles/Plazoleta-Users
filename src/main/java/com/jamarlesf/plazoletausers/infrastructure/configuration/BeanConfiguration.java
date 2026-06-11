package com.jamarlesf.plazoletausers.infrastructure.configuration;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.spi.IEncryptionPort;
import com.jamarlesf.plazoletausers.domain.spi.IRolePersistencePort;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;
import com.jamarlesf.plazoletausers.domain.usecase.UserUseCase;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository.IRoleRepository;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    private final IEncryptionPort encryptionPort;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), encryptionPort);
    }
}
