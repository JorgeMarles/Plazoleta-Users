package com.jamarlesf.plazoletausers.domain.model;

import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    private final LocalDate fixedDate = LocalDate.of(2026, 6, 7);
    private final Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    private User.UserBuilder validPropietarioBuilder() {
        return User.builder()
                .name("Jorge")
                .surname("Marles")
                .email("test@example.com")
                .phone("+1234567890")
                .documentId("123456789")
                .password("securePass123")
                .birthDate(LocalDate.of(2000, 5, 20))
                .role(new Role(2L, Role.PROPIETARIO, "Propietario"));
    }

    private User.UserBuilder validEmpleadoBuilder() {
        return User.builder()
                .name("Carlos")
                .surname("Lopez")
                .email("carlos@example.com")
                .phone("+1234567890")
                .documentId("987654321")
                .password("securePass456")
                .role(new Role(3L, Role.EMPLEADO, "Empleado"));
    }

    // ======== birthDate validation (only for PROPIETARIO) ========

    @Test
    void shouldBeValidWhenPropietarioIsAdult() {
        User user = validPropietarioBuilder()
                .birthDate(LocalDate.of(2000, 5, 20)) // 26 years old
                .build();

        assertDoesNotThrow(() -> user.validate(fixedClock));
    }

    @Test
    void shouldBeValidWhenPropietarioIsExactly18YearsOld() {
        User user = validPropietarioBuilder()
                .birthDate(LocalDate.of(2008, 6, 7)) // exactly 18
                .build();

        assertDoesNotThrow(() -> user.validate(fixedClock));
    }

    @Test
    void shouldThrowExceptionWhenPropietarioIsAlmost18YearsOld() {
        User user = validPropietarioBuilder()
                .birthDate(LocalDate.of(2008, 6, 8)) // 17 years, 364 days
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPropietarioIsUnderage() {
        User user = validPropietarioBuilder()
                .birthDate(LocalDate.of(2010, 5, 20)) // 16 years old
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPropietarioBirthDateIsNull() {
        User user = validPropietarioBuilder()
                .birthDate(null)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

    @Test
    void shouldNotValidateBirthDateForEmpleado() {
        User user = validEmpleadoBuilder()
                .birthDate(null) // no birthDate, should be fine for EMPLEADO
                .build();

        assertDoesNotThrow(() -> user.validate(fixedClock));
    }

    // ======== validateName ========

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        User user = validEmpleadoBuilder()
                .name(null)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El nombre del usuario no puede estar vacio", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        User user = validEmpleadoBuilder()
                .name("")
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El nombre del usuario no puede estar vacio", exception.getMessage());
    }

    // ======== validateSurname ========

    @Test
    void shouldThrowExceptionWhenSurnameIsNull() {
        User user = validEmpleadoBuilder()
                .surname(null)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El apellido del usuario no puede estar vacio", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSurnameIsEmpty() {
        User user = validEmpleadoBuilder()
                .surname("")
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El apellido del usuario no puede estar vacio", exception.getMessage());
    }

    // ======== validatePassword ========

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        User user = validEmpleadoBuilder()
                .password(null)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("La contraseña no puede estar vacia", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        User user = validEmpleadoBuilder()
                .password("")
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("La contraseña no puede estar vacia", exception.getMessage());
    }
}
