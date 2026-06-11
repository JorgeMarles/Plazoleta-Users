package com.jamarlesf.plazoletausers.infrastructure.out.jpa.mapper;

import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {
    UserEntity toEntity(User user);
    User toUser(UserEntity userEntity);
    List<User> toUserList(List<UserEntity> userEntityList);
}
