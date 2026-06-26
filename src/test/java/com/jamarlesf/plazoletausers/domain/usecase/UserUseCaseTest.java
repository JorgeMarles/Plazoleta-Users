package com.jamarlesf.plazoletausers.domain.usecase;

import com.jamarlesf.plazoletausers.domain.exception.DomainException;
import com.jamarlesf.plazoletausers.domain.exception.RoleNotFoundException;
import com.jamarlesf.plazoletausers.domain.exception.UserEmailAlreadyExistsException;
import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.IEncryptionPort;
import com.jamarlesf.plazoletausers.domain.spi.IRolePersistencePort;
import com.jamarlesf.plazoletausers.domain.spi.ITokenProviderPort;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IEncryptionPort encryptionPort;

    @Mock
    private ITokenProviderPort tokenProviderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;
    private Role validRole;

    @BeforeEach
    void setUp() {
        validRole = new Role(1L, "PROPIETARIO", "Propietario de restaurante");

        validUser = User.builder()
                .name("Juan")
                .surname("Pérez")
                .documentId("1234567890")
                .phone("+573001234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("juan.perez@example.com")
                .password("plainPassword123")
                .role(validRole)
                .build();
    }

    @Test
    void save_WhenValidUser_ShouldSaveSuccessfully() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(validRole));
        when(encryptionPort.encryptPassword(anyString())).thenReturn("encryptedPassword");

        userUseCase.save(validUser, Role.ADMINISTRADOR);

        verify(userPersistencePort).existsByEmail(validUser.getEmail());
        verify(rolePersistencePort).findById(validRole.getId());
        verify(encryptionPort).encryptPassword("plainPassword123");
        verify(userPersistencePort).save(validUser);
        assertEquals("encryptedPassword", validUser.getPassword());
        assertEquals(validRole, validUser.getRole());
    }

    @Test
    void save_WhenEmailAlreadyExists_ShouldThrowUserEmailAlreadyExistsException() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UserEmailAlreadyExistsException.class, () -> userUseCase.save(validUser, Role.ADMINISTRADOR));

        verify(userPersistencePort).existsByEmail(validUser.getEmail());
        verify(rolePersistencePort, never()).findById(anyLong());
        verify(encryptionPort, never()).encryptPassword(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void save_WhenRoleNotFound_ShouldThrowRoleNotFoundException() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userUseCase.save(validUser, Role.ADMINISTRADOR));

        verify(userPersistencePort).existsByEmail(validUser.getEmail());
        verify(rolePersistencePort).findById(validRole.getId());
        verify(encryptionPort, never()).encryptPassword(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @ParameterizedTest
    @CsvSource({
            "invalid-email, 1234567890, +573001234567",
            "@example.com, 1234567890, +573001234567",
            "user@, 1234567890, +573001234567",
            "user, 1234567890, +573001234567",
            "user@.com, 1234567890, +573001234567",
            "'user @example.com', 1234567890, +573001234567",
            "'', 1234567890, +573001234567",
            "juan.perez@example.com, ABC123, +573001234567",
            "juan.perez@example.com, 123ABC, +573001234567",
            "juan.perez@example.com, 12-34-56, +573001234567",
            "juan.perez@example.com, '', +573001234567",
            "juan.perez@example.com, 12.345.678, +573001234567",
            "juan.perez@example.com, 1234567890, 12345678901234",
            "juan.perez@example.com, 1234567890, abc123",
            "juan.perez@example.com, 1234567890, +12345678901234",
            "juan.perez@example.com, 1234567890, 300-123-4567",
            "juan.perez@example.com, 1234567890, ''"
    })
    void save_WhenInvalidUserData_ShouldThrowDomainException(String email, String documentId, String phone) {
        User invalidUser = User.builder()
                .name("Juan")
                .surname("Pérez")
                .documentId(documentId)
                .phone(phone)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email(email)
                .password("plainPassword123")
                .role(validRole)
                .build();

        assertThrows(DomainException.class, () -> userUseCase.save(invalidUser, Role.ADMINISTRADOR));

        verify(userPersistencePort, never()).existsByEmail(anyString());
        verify(rolePersistencePort, never()).findById(anyLong());
        verify(encryptionPort, never()).encryptPassword(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void save_WhenUserIsMinor_ShouldThrowDomainException() {
        User minorUser = User.builder()
                .name("Juan")
                .surname("Pérez")
                .documentId("1234567890")
                .phone("+573001234567")
                .birthDate(LocalDate.now().minusYears(17))
                .email("juan.perez@example.com")
                .password("plainPassword123")
                .role(validRole)
                .build();

        assertThrows(DomainException.class, () -> userUseCase.save(minorUser, Role.ADMINISTRADOR));

        verify(userPersistencePort, never()).existsByEmail(anyString());
        verify(rolePersistencePort, never()).findById(anyLong());
        verify(encryptionPort, never()).encryptPassword(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        User user1 = User.builder()
                .id(1L)
                .name("Juan")
                .surname("Pérez")
                .email("juan.perez@example.com")
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("María")
                .surname("González")
                .email("maria.gonzalez@example.com")
                .build();

        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userPersistencePort.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userUseCase.findAll();

        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
        verify(userPersistencePort).findAll();
    }

    @Test
    void findAll_WhenNoUsers_ShouldReturnEmptyList() {
        when(userPersistencePort.findAll()).thenReturn(List.of());

        List<User> actualUsers = userUseCase.findAll();

        assertNotNull(actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(userPersistencePort).findAll();
    }

    @Test
    void save_ShouldEncryptPasswordBeforeSaving() {
        String plainPassword = "plainPassword123";
        String encryptedPassword = "encryptedPassword456";

        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(validRole));
        when(encryptionPort.encryptPassword(plainPassword)).thenReturn(encryptedPassword);

        userUseCase.save(validUser, Role.ADMINISTRADOR);

        verify(encryptionPort).encryptPassword(plainPassword);
        assertEquals(encryptedPassword, validUser.getPassword());
    }

    @Test
    void save_ShouldSetRoleFromPersistence() {
        Role persistedRole = new Role(1L, "PROPIETARIO", "Updated description");

        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(persistedRole));
        when(encryptionPort.encryptPassword(anyString())).thenReturn("encryptedPassword");

        userUseCase.save(validUser, Role.ADMINISTRADOR);

        assertEquals(persistedRole, validUser.getRole());
        verify(rolePersistencePort).findById(validRole.getId());
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        User user = User.builder()
                .id(1L)
                .name("Juan")
                .surname("Pérez")
                .email("juan.perez@example.com")
                .role(validRole)
                .build();

        when(userPersistencePort.findById(1L)).thenReturn(user);

        User foundUser = userUseCase.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("Juan", foundUser.getName());
        assertEquals("juan.perez@example.com", foundUser.getEmail());
        verify(userPersistencePort).findById(1L);
    }

    @Test
    void findById_WhenUserNotFound_ShouldReturnNull() {
        when(userPersistencePort.findById(99L)).thenReturn(null);

        User foundUser = userUseCase.findById(99L);

        assertNull(foundUser);
        verify(userPersistencePort).findById(99L);
    }

    @Test
    void login_WhenValid_ShouldReturnToken() {
        String email = validUser.getEmail();
        String password = validUser.getPassword();

        when(userPersistencePort.findByEmail("juan.perez@example.com")).thenReturn(Optional.of(validUser));
        when(encryptionPort.matches("plainPassword123", validUser.getPassword())).thenReturn(true);
        when(tokenProviderPort.generateToken(validUser)).thenReturn(String.valueOf(validUser.getId()));

        String token = userUseCase.login(email, password);

        assertEquals(String.valueOf(validUser.getId()), token);
    }

    @Test
    void login_WhenEmailNotFound_ShouldThrowDomainException() {
        String email = validUser.getEmail();
        String password = validUser.getPassword();

        when(userPersistencePort.findByEmail("juan.perez@example.com")).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.login(email, password));
        assertEquals("Correo o contraseña incorrectos", exception.getMessage());
    }

    @Test
    void login_WhenInvalidPassword_ShouldThrowDomainException() {
        String email = validUser.getEmail();
        String password = "plainPassword1234";

        when(userPersistencePort.findByEmail("juan.perez@example.com")).thenReturn(Optional.of(validUser));
        when(encryptionPort.matches("plainPassword1234", validUser.getPassword())).thenReturn(false);


        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.login(email, password));
        assertEquals("Correo o contraseña incorrectos", exception.getMessage());
    }

    @Test
    void save_AsNonAdminCreatingPropietario_ShouldThrowDomainException() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(validRole)); // validRole is PROPIETARIO

        DomainException exception = assertThrows(DomainException.class, 
                () -> userUseCase.save(validUser, Role.EMPLEADO));

        assertEquals("No tiene los permisos para realizar esta acción", exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void save_AsPropietarioCreatingEmpleado_ShouldSaveSuccessfully() {
        Role empleadoRole = new Role(2L, Role.EMPLEADO, "Empleado");
        User empleadoUser = User.builder()
                .name("Pedro")
                .surname("Gómez")
                .documentId("0987654321")
                .phone("+573007654321")
                .email("pedro.gomez@example.com")
                .password("plainPassword123")
                .role(empleadoRole)
                .build();

        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(empleadoRole));
        when(encryptionPort.encryptPassword(anyString())).thenReturn("encryptedPassword");

        userUseCase.save(empleadoUser, Role.PROPIETARIO);

        verify(userPersistencePort).save(empleadoUser);
        assertEquals(empleadoRole, empleadoUser.getRole());
    }

    @Test
    void save_AsNonPropietarioCreatingEmpleado_ShouldThrowDomainException() {
        Role empleadoRole = new Role(2L, Role.EMPLEADO, "Empleado");
        User empleadoUser = User.builder()
                .name("Pedro")
                .surname("Gómez")
                .documentId("0987654321")
                .phone("+573007654321")
                .email("pedro.gomez@example.com")
                .password("plainPassword123")
                .role(empleadoRole)
                .build();

        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(rolePersistencePort.findById(anyLong())).thenReturn(Optional.of(empleadoRole));

        DomainException exception = assertThrows(DomainException.class, 
                () -> userUseCase.save(empleadoUser, Role.ADMINISTRADOR));

        assertEquals("No tiene los permisos para realizar esta acción", exception.getMessage());
        verify(userPersistencePort, never()).save(any(User.class));
    }
}
