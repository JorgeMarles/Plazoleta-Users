package com.jamarlesf.plazoletausers.infrastructure.out.jpa.adapter;

import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.domain.spi.IRolePersistencePort;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.RoleEntity;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public void save(Role role) {
        RoleEntity roleEntity = roleEntityMapper.toEntity(role);
        roleRepository.save(roleEntity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        return roleEntity.map(roleEntityMapper::toRole);
    }
}
