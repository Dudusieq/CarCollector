package com.example.CarCollector.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefreshTokenService {
    private final String refreshTokenSecret = "refreshSecretKey1234567890refreshSecretKey1234567890refreshSecretKey1234567890refreshSecretKey1234567890refreshSecretKey1234567890";
    private final long refreshTokenExpirationMs = 7 * 24 * 60 * 60 * 1000; // 7dni

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, refreshTokenSecret)
                .compact();
    }

    public boolean verifyRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refreshTokenSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsernameFromRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(refreshTokenSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
