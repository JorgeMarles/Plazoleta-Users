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

    @Test
    void shouldBeValidWhenUserIsAdult() {
        // Arrange
        LocalDate validBirthDate = LocalDate.of(2000, 5, 20); // 26 years old

        User user = User.builder()
                .email("test@example.com")
                .phone("+1234567890")
                .documentId("123456789")
                .birthDate(validBirthDate)
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> user.validate(fixedClock));
    }

    @Test
    void shouldBeValidWhenUserIsExactly18YearsOld() {
        // Arrange
        LocalDate validBirthDate = LocalDate.of(2008, 6, 7); // 18 years old as of fixedDate

        User user = User.builder()
                .email("test@example.com")
                .phone("+1234567890")
                .documentId("123456789")
                .birthDate(validBirthDate)
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> user.validate(fixedClock));
    }

    @Test
    void shouldThrowExceptionWhenUserIsAlmost18YearsOld() {
        // Arrange
        LocalDate validBirthDate = LocalDate.of(2008, 6, 8); // 17 years old as of fixedDate

        User user = User.builder()
                .email("test@example.com")
                .phone("+1234567890")
                .documentId("123456789")
                .birthDate(validBirthDate)
                .build();

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderage() {
        // Arrange
        LocalDate invalidBirthDate = LocalDate.of(2010, 5, 20); // 16 years old

        User user = User.builder()
                .email("test@example.com")
                .phone("+1234567890")
                .documentId("123456789")
                .birthDate(invalidBirthDate)
                .build();

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> user.validate(fixedClock));
        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }
}
