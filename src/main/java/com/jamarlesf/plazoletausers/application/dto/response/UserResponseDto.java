package com.jamarlesf.plazoletausers.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String documentId;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private RoleResponseDto role;
}
