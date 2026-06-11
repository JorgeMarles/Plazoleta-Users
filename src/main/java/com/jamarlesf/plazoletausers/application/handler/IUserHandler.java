package com.jamarlesf.plazoletausers.application.handler;

import com.jamarlesf.plazoletausers.application.dto.request.UserRequestDto;
import com.jamarlesf.plazoletausers.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto user);
    List<UserResponseDto> getUsers();
}
