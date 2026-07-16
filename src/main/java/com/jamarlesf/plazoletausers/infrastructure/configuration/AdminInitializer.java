package com.jamarlesf.plazoletausers.infrastructure.configuration;

import com.jamarlesf.plazoletausers.domain.api.IUserServicePort;
import com.jamarlesf.plazoletausers.domain.model.Role;
import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.IUserPersistencePort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdminUser(IUserServicePort userServicePort, IUserPersistencePort userPersistencePort) {
        return args -> {
            String adminEmail = "admin@gmail.com";
            if (!userPersistencePort.existsByEmail(adminEmail)) {
                Role adminRole = new Role();
                adminRole.setId(1L);
                
                User adminUser = User.builder()
                        .name("Jorge")
                        .surname("Marles")
                        .documentId("111111111")
                        .phone("+573333333333")
                        .birthDate(LocalDate.of(2004, 10, 27))
                        .email(adminEmail)
                        .password("12345678")
                        .role(adminRole)
                        .build();

                userServicePort.save(adminUser, Role.ADMINISTRADOR);
            }
        };
    }
}
