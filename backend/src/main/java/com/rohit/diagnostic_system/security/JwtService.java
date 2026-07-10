package com.rohit.diagnostic_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {
    private final SecretKey signingKey;
    private final long expiration;

    public JwtService(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.expiration}") long expiration) {
        if (secret.length() < 32) {
            log.error("JWT secret validation failed: configured secret is shorter than 32 characters");
            throw new IllegalArgumentException("JWT_SECRET must be at least 32 characters long");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        log.info("JWT service initialized expirationMs={}", expiration);
    }

    public String generateToken(String email, UUID userId, String role) {
        Date now = new Date();
        log.debug("Generating JWT userId={} role={}", userId, role);
        return Jwts.builder().subject(email).claim("userId", userId.toString()).claim("role", role)
                .issuedAt(now).expiration(new Date(now.getTime() + expiration)).signWith(signingKey).compact();
    }

    public Claims extractClaims(String token) {
        log.debug("Extracting JWT claims");
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}
