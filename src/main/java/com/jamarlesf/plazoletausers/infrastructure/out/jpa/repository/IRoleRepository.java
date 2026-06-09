package com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository;

import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

}
