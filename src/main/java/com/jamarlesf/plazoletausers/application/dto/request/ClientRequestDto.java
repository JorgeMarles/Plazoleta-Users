package com.jamarlesf.plazoletausers.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDto {
    private String name;
    private String surname;
    private String documentId;
    private String phone;
    private String email;
    private String password;
}
