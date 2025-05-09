package com.example.CarCollector.controller;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.security.JwtService;
import com.example.CarCollector.security.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtService jwtService;
    @Autowired
    RefreshTokenService refreshTokenService;

    private String validRefreshToken;


    @BeforeEach
    void setUp() {
        validRefreshToken = refreshTokenService.generateRefreshToken("user123");
    }

    @Test
    void login_withValidCredentials_returnsAccessAndRefreshToken() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("user123");
        req.setPassword("test123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void login_withInvalidCredentials_returnsUnauthorized() throws Exception {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("bad");
        req.setPassword("bad");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid username or password"));
    }

    @Test
    void refresh_withValidRefreshToken_returnsNewAccessToken() throws Exception {
        // Użycie właściwej klasy DTO:
        RefreshTokenRequestDTO req = new RefreshTokenRequestDTO();
        req.setRefreshToken(validRefreshToken);

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void refresh_withInvalidRefreshToken_returnsUnauthorized() throws Exception {
        RefreshTokenRequestDTO req = new RefreshTokenRequestDTO();
        req.setRefreshToken("invalid");

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid refresh token"));
    }
}
