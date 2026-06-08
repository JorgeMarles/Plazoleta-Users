package com.jamarlesf.plazoletausers.application.mapper;

import com.jamarlesf.plazoletausers.application.dto.response.UserResponseDto;
import com.jamarlesf.plazoletausers.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface IUserResponseMapper {
    UserResponseDto toResponse(User user);

    List<UserResponseDto> toResponseList(List<User> users);
}
