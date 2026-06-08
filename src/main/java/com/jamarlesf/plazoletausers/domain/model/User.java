package com.jamarlesf.plazoletausers.domain.model;

import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String name;
    private String surname;
    private String documentId;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Role rol;

    private void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new DomainException("Estructura de email inválida");
    }

    private void validatePhone(String phone) {
        if (phone == null || !phone.matches("^\\+?\\d{1,12}$"))
            throw new DomainException("El teléfono debe tener máximo 13 caracteres y solo puede iniciar con +");
    }

    private void validateDocumentId(String documentId) {
        if (documentId == null || !documentId.matches("^\\d+$"))
            throw new DomainException("El documento de identidad debe ser únicamente numérico");
    }

    private void validateAdult(LocalDate birthDate) {
        if (birthDate == null || Period.between(birthDate, LocalDate.now()).getYears() < 18)
            throw new DomainException("El usuario debe ser mayor de edad");
    }

    private User validate() {
        validateEmail(this.email);
        validatePhone(this.phone);
        validateDocumentId(this.documentId);
        validateAdult(this.birthDate);
        return this;
    }

    public static UserBuilder builder() {
        return new UserBuilder() {
            @Override
            public User build() {
                return super.build().validate();
            }
        };
    }

}
