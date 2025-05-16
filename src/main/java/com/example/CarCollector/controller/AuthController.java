package com.example.CarCollector.controller;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.repository.UserRepository;
import com.example.CarCollector.security.JwtService;
import com.example.CarCollector.security.RefreshTokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        logger.info("AuthController initialized");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequestDTO req) {
        return userRepository.findByUsername(req.getUsername())
                .map(u -> {
                    String raw  = req.getPassword();
                    String hash = u.getPassword();
                    boolean ok   = passwordEncoder.matches(raw, hash);

                    logger.debug("Login attempt for {}: raw='{}', hash='{}'", u.getUsername(), raw, hash);
                    logger.debug("passwordEncoder.matches -> {}", ok);

                    if (!ok) {
                        logger.warn("Invalid login attempt for username: {}", u.getUsername());
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error","Invalid username or password"));
                    }


                    String accessToken  = jwtService.generateToken(u.getUsername());
                    String refreshToken = refreshTokenService.generateRefreshToken(u.getUsername());

                    logger.info("Generated accessToken={} and refreshToken={} for user={}",
                            accessToken, refreshToken, u.getUsername());


                    Map<String,String> body = new HashMap<>();
                    body.put("accessToken",  accessToken);
                    body.put("refreshToken", refreshToken);
                    return ResponseEntity.ok(body);
                })
                .orElseGet(() -> {
                    logger.warn("Login attempt for unknown user: {}", req.getUsername());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error","Invalid username or password"));
                });
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
    // dla wygenerowania hasla
    /*@GetMapping("/debug/encode")
    public String encode(@RequestParam String p) {
        return passwordEncoder.encode(p);
    }*/

}
