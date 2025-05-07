package com.example.CarCollector.controller;

import com.example.CarCollector.security.JwtToken;
import com.example.CarCollector.security.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtToken tokenP;
    private final RefreshTokenService refreshTokenService;

    public AuthController(JwtToken tokenP, RefreshTokenService refreshTokenService) {
        this.tokenP = tokenP;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        if (!"user".equals(loginRequest.getUsername()) || !"secret".equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błędne dane logowania");
        }

        String accessToken = tokenP.generateToken(loginRequest.getUsername());
        String refreshToken = refreshTokenService.generateRefreshToken(loginRequest.getUsername());

        return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        if (!refreshTokenService.verifyRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String username = refreshTokenService.getUsernameFromRefreshToken(refreshToken);
        String newAccessToken = tokenP.generateToken(username);
        String newRefreshToken = refreshTokenService.generateRefreshToken(username);

        return ResponseEntity.ok(new JwtAuthenticationResponse(newAccessToken, newRefreshToken));
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class JwtAuthenticationResponse {
        private final String token;
        private final String refreshToken;

        public JwtAuthenticationResponse(String token, String refreshToken) {
            this.token = token;
            this.refreshToken = refreshToken;
        }

        public String getToken() {
            return token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }

    @Data
    public static class RefreshTokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
