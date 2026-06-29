package com.jamarlesf.plazoletausers.application.handler;

import com.jamarlesf.plazoletausers.application.dto.request.ClientRequestDto;
import com.jamarlesf.plazoletausers.application.dto.request.LoginRequestDto;
import com.jamarlesf.plazoletausers.application.dto.request.UserRequestDto;
import com.jamarlesf.plazoletausers.application.dto.response.TokenResponseDto;
import com.jamarlesf.plazoletausers.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto user, String requestUserRole);
    void saveClient(ClientRequestDto clientRequestDto);
    List<UserResponseDto> getUsers();
    UserResponseDto getUserById(Long id);
    TokenResponseDto login(LoginRequestDto loginRequestDto);
}
