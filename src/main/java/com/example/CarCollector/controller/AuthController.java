package com.example.CarCollector.controller;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.exception.BadRequestException;
import com.example.CarCollector.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO request) {
        System.out.println("Login attempt for user: " + request.getUsername() + " with password: " + request.getPassword());

        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null && password == null || username.isEmpty() && password.isEmpty()) {
            throw new BadRequestException("Username or password is required");
        }

        String token = jwtService.generateToken(username);
        System.out.println("Login successful for user: " + username);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@RequestParam String token) {
        System.out.println("Token verification request received");

        if (jwtService.verifyToken(token)) {
            String username = jwtService.getUsernameFromJwt(token);
            System.out.println("Token verified successfully for user: " + username);
            return ResponseEntity.ok(Map.of("message", "Token valid for user: " + username));
        }

        throw new BadRequestException("Invalid token");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
        System.out.println("Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
