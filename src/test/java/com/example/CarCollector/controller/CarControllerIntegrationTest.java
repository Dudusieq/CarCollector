package com.example.CarCollector.controller;

import com.example.CarCollector.dto.CarDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerIntegrationTest {


    private final TestRestTemplate restTemplate;

    public CarControllerIntegrationTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void getAllCars_returnsValidResponse() {
        ResponseEntity<CarDTO[]> response = restTemplate.getForEntity("/cars", CarDTO[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
