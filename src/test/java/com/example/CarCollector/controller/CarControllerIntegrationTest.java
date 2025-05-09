package com.example.CarCollector.controller;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;
import com.example.CarCollector.repository.CarRepository;
import com.example.CarCollector.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CarRepository carRepository;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtService.generateToken("user123");
        carRepository.deleteAll();
    }

    @Test
    void addCar_and_getAllCars_and_deleteCar_flow() throws Exception {
        CarDTO dto = new CarDTO();
        dto.setBrandcar("BMW");
        dto.setModel("X5");
        dto.setPower(300);
        dto.setSpeedUp(5.5);
        dto.setEngine("V8");

        // Add
        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandcar").value("BMW"));

        // Get all
        mockMvc.perform(get("/cars")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Delete
        Long id = carRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/cars/" + id)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());


        mockMvc.perform(get("/cars")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getCars_withoutToken_forbidden() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isForbidden());
    }
}
