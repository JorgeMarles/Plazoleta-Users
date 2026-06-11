package com.jamarlesf.plazoletausers.application.mapper;

import com.jamarlesf.plazoletausers.application.dto.request.UserRequestDto;
import com.jamarlesf.plazoletausers.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {
    @Mapping(target = "role.id", source = "roleId")
    User toUser(UserRequestDto userRequestDto);
}
