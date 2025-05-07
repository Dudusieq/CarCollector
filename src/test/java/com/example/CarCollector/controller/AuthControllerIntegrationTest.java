package com.example.CarCollector.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

    private final TestRestTemplate restTemplate;

    public AuthControllerIntegrationTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void login_returnsJwtTokens() {
        String loginUrl = "/auth/login";


        String loginJson = "{\"username\":\"user\", \"password\":\"secret\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(loginJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("token"));
    }
    @Test
    void login_returnsJwtToken_whenCredentialsAreValid() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String loginJson = "{\"username\":\"user\", \"password\":\"secret\"}";
        HttpEntity<String> request = new HttpEntity<>(loginJson, headers);


        ResponseEntity<AuthResponse> response = restTemplate.postForEntity("/auth/login", request, AuthResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AuthResponse authResponse = response.getBody();
        assertNotNull(authResponse, "Odpowiedź nie może być pusta");
        assertNotNull(authResponse.getToken(), "Brak tokena dostępu");
    }

    static class AuthResponse {
        private String token;

        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }
}
