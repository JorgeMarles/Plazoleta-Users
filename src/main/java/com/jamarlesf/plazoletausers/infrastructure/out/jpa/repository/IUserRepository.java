package com.jamarlesf.plazoletausers.infrastructure.out.jpa.repository;

import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
}
