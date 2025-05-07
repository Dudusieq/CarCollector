package com.example.CarCollector.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {
    //Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final String jwtSecretKey = "mySecret123456mySecret123456mySecret123456mySecret123456mySecret123456mySecret123456mySecret123456";
    private final long jwtExpirationMs = 3600000;

    // Generowanie tokenu
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }

    // Pozyskiwanie nazwy użytkownika z tokenu
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Weryfikacja tokenu
    public boolean verifyToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Błąd walidacji JWT: " + e.getMessage());
        }
        return false;
    }


}
