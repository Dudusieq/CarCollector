package com.example.CarCollector.map;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public List<String> rolesToStrings(List<String> roles) {
        return roles.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}
