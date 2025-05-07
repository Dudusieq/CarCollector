package com.example.CarCollector.controller;

import com.example.CarCollector.security.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtToken tokenP;

    public AuthController(JwtToken tokenP) {
        this.tokenP = tokenP;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Otrzymano żądanie logowania od: " + loginRequest.getUsername());


        if (!("user".equals(loginRequest.getUsername()) && "secret".equals(loginRequest.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błędne dane logowania");
        }


        String token = tokenP.generateToken(loginRequest.getUsername());


        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
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

    @Data
    public static class JwtAuthenticationResponse {
        private final String token;

        public JwtAuthenticationResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
