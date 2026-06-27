package com.jamarlesf.plazoletausers.domain.model;

import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Clock;
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
    private Role role;

    private void validateEmail() {
        if (this.email == null || !this.email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new DomainException("Estructura de email inválida");
    }

    private void validatePhone() {
        if (this.phone == null || !this.phone.matches("^\\+?\\d{1,12}$"))
            throw new DomainException("El teléfono debe tener máximo 13 caracteres y solo puede iniciar con +");
    }

    private void validateDocumentId() {
        if (this.documentId == null || !this.documentId.matches("^\\d+$"))
            throw new DomainException("El documento de identidad debe ser únicamente numérico");
    }

    private void validateAdult(Clock clock) {
        if (this.birthDate == null || Period.between(this.birthDate, LocalDate.now(clock)).getYears() < 18)
            throw new DomainException("El usuario debe ser mayor de edad");
    }

    private void validateName() {
        if(this.name == null || this.name.isEmpty()) {
            throw new DomainException("El nombre del usuario no puede estar vacio");
        }
    }

    private void validateSurname() {
        if(this.surname == null || this.surname.isEmpty()) {
            throw new DomainException("El apellido del usuario no puede estar vacio");
        }
    }

    private void validatePassword() {
        if(this.password == null || this.password.isEmpty()) {
            throw new DomainException("La contraseña no puede estar vacia");
        }
    }

    public void validate(Clock clock) {
        validateName();
        validateSurname();
        validateEmail();
        validatePhone();
        validateDocumentId();
        validatePassword();
        if(Role.PROPIETARIO.equals(this.role.getName())) {
            validateAdult(clock);
        }
    }

    public void validate() {
        validate(Clock.systemDefaultZone());
    }

}
