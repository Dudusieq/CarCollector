package com.example.CarCollector.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO {

    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
