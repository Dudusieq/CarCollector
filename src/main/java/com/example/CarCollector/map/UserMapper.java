package com.example.CarCollector.map;

import com.example.CarCollector.dto.UserDTO;
import com.example.CarCollector.dto.UserResponseDTO;
import com.example.CarCollector.dto.UserUpdateDTO;
import com.example.CarCollector.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserResponseDTO toDto(User user);

    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    User toEntity(UserDTO dto, @Context org.springframework.security.crypto.password.PasswordEncoder passwordEncoder);

    void updateFromDto(UserUpdateDTO dto, @MappingTarget User user);
}
