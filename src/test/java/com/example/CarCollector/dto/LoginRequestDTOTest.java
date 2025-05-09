package com.example.CarCollector.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validDTO_noViolations() {
        var dto = new LoginRequestDTO();
        dto.setUsername("user");
        dto.setPassword("pass");
        Set<ConstraintViolation<LoginRequestDTO>> v = validator.validate(dto);
        assertTrue(v.isEmpty());
    }

    @Test
    void blankUsername_violation() {
        var dto = new LoginRequestDTO();
        dto.setUsername("");
        dto.setPassword("pass");
        Set<ConstraintViolation<LoginRequestDTO>> v = validator.validate(dto);
        assertFalse(v.isEmpty());
    }

    @Test
    void blankPassword_violation() {
        var dto = new LoginRequestDTO();
        dto.setUsername("user");
        dto.setPassword("");
        Set<ConstraintViolation<LoginRequestDTO>> v = validator.validate(dto);
        assertFalse(v.isEmpty());
    }
}
