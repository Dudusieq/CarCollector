package com.example.CarCollector.security;

import com.example.CarCollector.dto.LoginRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired JwtService jwtService;
    @Autowired RefreshTokenService refreshTokenService;

    private String jwt;
    private String refreshToken;

    @BeforeEach
    void setUp() throws Exception {
        // Logowanie i pobranie tokenów
        LoginRequestDTO login = new LoginRequestDTO();
        login.setUsername("user123");
        login.setPassword("test123");

        String loginResp = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode node = objectMapper.readTree(loginResp);
        jwt = node.get("accessToken").asText();
        refreshToken = node.get("refreshToken").asText();
    }

    @Test
    void loginEndpointPermittedWithoutAuth() throws Exception {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setUsername("user123");
        login.setPassword("test123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }

    @Test
    void refreshEndpointPermittedWithoutAuth() throws Exception {
        String body = "{\"refreshToken\":\"" + refreshToken + "\"}";

        mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void protectedEndpointWithoutAuth_forbidden() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isForbidden());
    }

    @Test
    void protectedEndpointWithAuth_allowed() throws Exception {
        mockMvc.perform(get("/cars")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}
