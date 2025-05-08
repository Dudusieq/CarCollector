package com.example.CarCollector.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenTest {

    private final JwtService jwtToken = new JwtService();

    @Test
    void generateToken_andValidateToken() {
        String username = "user";
        String token = jwtToken.generateToken(username);

        assertNotNull(token, "Token powinien być wygenerowany");
        assertTrue(jwtToken.verifyToken(token), "Wygenerowany token powinien być prawidłowy");

        String extractedUsername = jwtToken.getUsernameFromJwt(token);
        assertEquals(username, extractedUsername, "Pobrana nazwa użytkownika powinna odpowiadać");
    }
}
