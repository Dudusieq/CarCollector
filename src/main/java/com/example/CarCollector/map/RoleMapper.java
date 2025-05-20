package com.example.CarCollector.map;

import com.example.CarCollector.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // konwersja String → Role
    Role stringToRole(String name);

    // konwersja Role → String
    default String roleToString(Role role) {
        return role == null ? null : role.getName();
    }
}
