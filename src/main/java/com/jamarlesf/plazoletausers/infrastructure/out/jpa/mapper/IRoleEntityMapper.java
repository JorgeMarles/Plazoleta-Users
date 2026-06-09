package com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper;

import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {
    RoleEntity toEntity(Role role);
    Role toRole(RoleEntity roleEntity);
    List<RoleEntity> toEntityList(List<Role> roleList);
}
