package com.example.CarCollector.dto;

import lombok.AllArgsConstructor;

import java.util.Set;


@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private Boolean active;
    private Set<String> roles;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getActive() {
        return active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
