package com.jamarlesf.plazoletausers.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {
    private String name;
    private String surname;
    private String documentId;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Long roleId;
}
