package com.example.CarCollector.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefreshTokenRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenRefreshTokenIsValid_thenNoConstraintViolations() {
        // zmiana testowej klasy na właściwą DTO:
        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
        dto.setRefreshToken("validToken123");

        Set<ConstraintViolation<RefreshTokenRequestDTO>> violations = validator.validate(dto);

        assertEquals(0, violations.size());
    }

    @Test
    void whenRefreshTokenIsBlank_thenConstraintViolation() {
        RefreshTokenRequestDTO dto = new RefreshTokenRequestDTO();
        dto.setRefreshToken("");

        Set<ConstraintViolation<RefreshTokenRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Refresh token cannot be blank", violations.iterator().next().getMessage());
    }
}
