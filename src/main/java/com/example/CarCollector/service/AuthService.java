package com.example.CarCollector.service;

import com.example.CarCollector.dto.AuthResponseDTO;
import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.model.User;
import com.example.CarCollector.repository.UserRepository;
import com.example.CarCollector.security.JwtService;
import com.example.CarCollector.security.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponseDTO login(LoginRequestDTO req) {
        log.info("Login attempt for user: {}", req.getUsername());
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> {
                    log.warn("User not found: {}", req.getUsername());
                    return new RuntimeException("Invalid username or password");
                });

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", req.getUsername());
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken  = jwtService.generateToken(user.getUsername());
        String refreshToken = refreshTokenService.generateRefreshToken(user.getUsername());
        log.info("Generated access and refresh tokens for user: {}", user.getUsername());

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public String refresh(RefreshTokenRequestDTO req) {
        log.info("Refresh token request received");
        log.debug("Refresh token: {}", req.getRefreshToken());

        if (!refreshTokenService.verifyRefreshToken(req.getRefreshToken())) {
            log.warn("Refresh token validation failed");
            throw new RuntimeException("Invalid refresh token");
        }

        String username = refreshTokenService.getUsernameFromRefreshToken(req.getRefreshToken());
        String newAccessToken = jwtService.generateToken(username);
        log.info("Issued new access token for user: {}", username);

        return newAccessToken;
    }
}