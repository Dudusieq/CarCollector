package com.example.CarCollector.dto;

import jakarta.validation.constraints.NotNull;

public class UserUpdateDTO {
    @NotNull
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
