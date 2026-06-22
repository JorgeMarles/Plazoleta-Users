package com.jamarlesf.plazoletausers.infrastructure.security.adapter;

import com.jamarlesf.plazoletausers.domain.model.User;
import com.jamarlesf.plazoletausers.domain.spi.ITokenProviderPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Service
public class JwtAdapter implements ITokenProviderPort {

    private static final Duration TOKEN_DURATION = Duration.ofHours(24);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TOKEN_DURATION.toMillis()))
                .signWith(key)
                .compact();
    }
}
