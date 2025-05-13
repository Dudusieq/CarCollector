package com.example.CarCollector.controller;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.security.JwtService;
import com.example.CarCollector.security.RefreshTokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        logger.info("AuthController initialized");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        logger.info("Login endpoint hit");

        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        logger.info("Username: {}",username);
        logger.info("Password: {}",password);

        // sprawdzene user i password
        if (!"test123".equals(password) || !"user123".equals(username)) {
            logger.warn("Invalid login attempt for username: {}",username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }

        /*        String storedEncodedPassword = authService.registerUser(username, password); // Tylko dla testów
        if (!passwordEncoder.matches(password, storedEncodedPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
        */

        String accessToken = jwtService.generateToken(username);
        String refreshToken = refreshTokenService.generateRefreshToken(username);

        logger.info("Generated access token for {}: {} ",username,accessToken);
        logger.info("Generated refresh token for {}: {} ",username,refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO requestDTO) {
        logger.info("Refresh token endpoint hit");

        String refreshToken = requestDTO.getRefreshToken();
        logger.debug("Received refresh token: {}",refreshToken);

        if (!refreshTokenService.verifyRefreshToken(refreshToken)) {
            logger.warn("Invalid refresh token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token"));
        }

        String username;
        try {
            username = refreshTokenService.getUsernameFromRefreshToken(refreshToken);
        } catch (Exception e) {
            logger.error("Error extracting username from refresh token: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token"));
        }

        String newAccessToken = jwtService.generateToken(username);
        logger.info("Generated new access token for {}: {}",username,newAccessToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
    }
}
