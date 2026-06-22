package com.jamarlesf.plazoletausers.application.handler.impl;

import com.jamarlesf.plazoletausers.application.dto.request.LoginRequestDto;
import com.jamarlesf.plazoletausers.application.dto.request.UserRequestDto;
import com.jamarlesf.plazoletausers.application.dto.response.TokenResponseDto;
import com.jamarlesf.plazoletausers.application.dto.response.UserResponseDto;
import com.jamarlesf.plazoletausers.application.handler.IUserHandler;
import com.jamarlesf.plazoletausers.application.mapper.IUserRequestMapper;
import com.jamarlesf.plazoletausers.application.mapper.IUserResponseMapper;
import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        User user = userRequestMapper.toUser(userRequestDto);
        userServicePort.save(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return userResponseMapper.toResponseList(userServicePort.findAll());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userResponseMapper.toResponse(userServicePort.findById(id));
    }

    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        return new TokenResponseDto(userServicePort.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    }
}
