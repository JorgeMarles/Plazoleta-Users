package com.jamarlesf.plazoletausers.infrastructure.out.security.adapter;

import com.jamarlesf.plazoletausers.domain.spi.IEncryptionPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordAdapter implements IEncryptionPort {

    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordAdapter() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encryptPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }
}
