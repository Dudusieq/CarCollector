package com.example.CarCollector.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenServiceTest {

    private RefreshTokenService service;

    @BeforeEach
    void setUp() {
        service = new RefreshTokenService();

        // Generujemy klucz dla testu secretkeyem
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        ReflectionTestUtils.setField(service, "refreshTokenSecret", base64Key);
        ReflectionTestUtils.setField(service, "refreshTokenExpirationMs", 7 * 24 * 60 * 60 * 1000L);
    }

    @Test
    void generateAndVerifyRefreshToken() {
        String token = service.generateRefreshToken("user123");
        assertTrue(service.verifyRefreshToken(token), "Token powinien być poprawnie zweryfikowany");
        assertEquals("user123", service.getUsernameFromRefreshToken(token),
                "Username wyciągnięty z tokenu powinien być taki sam");
    }

    @Test
    void invalidRefreshToken() {
        assertFalse(service.verifyRefreshToken("invalid"),
                "Nieprawidłowy token powinien być odrzucony");
    }
}
