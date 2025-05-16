package com.example.CarCollector.controller;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.model.User;
import com.example.CarCollector.repository.UserRepository;
import com.example.CarCollector.security.JwtService;
import com.example.CarCollector.security.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String validRefreshToken;

    private final String validUsername = "user1";
    private final String validPassword = "pass124";

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        String encodedPassword = passwordEncoder.encode(validPassword);
        logger.info("Encoded password: {}", encodedPassword);

        User user = new User();
        user.setUsername(validUsername);
        user.setPassword(encodedPassword);
        user.setActive(true);
        userRepository.save(user);

        validRefreshToken = refreshTokenService.generateRefreshToken(validUsername);
    }


    @Test
    void login_withValidCredentials_returns200() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(validUsername);
        loginRequest.setPassword(validPassword);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void login_withInvalidCredentials_returns401() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("invalidUser");
        loginRequest.setPassword("wrongPass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }

    @Test
    void refresh_withValidRefreshToken_returnsNewAccessToken() throws Exception {
        RefreshTokenRequestDTO req = new RefreshTokenRequestDTO();
        req.setRefreshToken(validRefreshToken);

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void refresh_withInvalidRefreshToken_returns401() throws Exception {
        RefreshTokenRequestDTO req = new RefreshTokenRequestDTO();
        req.setRefreshToken("invalidRefreshToken");

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid refresh token"));
    }
}
