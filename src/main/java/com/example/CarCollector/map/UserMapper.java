package com.example.CarCollector.map;

import com.example.CarCollector.dto.UserResponseDTO;
import com.example.CarCollector.model.Role;
import com.example.CarCollector.model.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserResponseDTO dto, @MappingTarget User user);

    // Mapowanie Set<Role> na Set<String>
    default Set<String> rolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    // Mapowanie Set<String> na Set<Role>
    default Set<Role> stringsToRoles(Set<String> roleNames) {
        if (roleNames == null) {
            return null;
        }
        return roleNames.stream()
                .map(name -> {
                    Role role = new Role();
                    role.setName(name);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
