package com.example.CarCollector.service;

import com.example.CarCollector.dto.UserDTO;
import com.example.CarCollector.dto.UserResponseDTO;
import com.example.CarCollector.dto.UserUpdateDTO;
import com.example.CarCollector.map.UserMapper;
import com.example.CarCollector.model.User;
import com.example.CarCollector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo,
                       UserMapper mapper,
                       PasswordEncoder encoder) {
        this.repo    = repo;
        this.mapper  = mapper;
        this.encoder = encoder;
    }

    public UserResponseDTO createUser(UserDTO dto) {
        log.info("Request to create user: {}", dto.getUsername());
        User entity = mapper.toEntity(dto, encoder);
        User saved  = repo.save(entity);
        log.debug("User created: id={}, username={}", saved.getId(), saved.getUsername());
        return mapper.toDto(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        log.info("Request to list all users");
        List<UserResponseDTO> dtos = repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
        log.debug("Returning {} users", dtos.size());
        return dtos;
    }

    public UserResponseDTO getUserById(Long id) {
        log.info("Request to get user by ID: {}", id);
        User user   = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: id={}", id);
                    return new IllegalArgumentException("User not found");
                });
        UserResponseDTO dto = mapper.toDto(user);
        log.debug("Found user: id={}, username={}", dto.getId(), dto.getUsername());
        return dto;
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        log.info("Request to update user id={} active={}", id, dto.getActive());
        User user   = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: id={}", id);
                    return new IllegalArgumentException("User not found");
                });
        mapper.updateFromDto(dto, user);
        User updated = repo.save(user);
        log.debug("User updated: id={}, active={}", updated.getId(), updated.getActive());
        return mapper.toDto(updated);
    }

    public void deleteUser(Long id) {
        log.info("Request to deactivate user id={}", id);
        User user = repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: id={}", id);
                    return new IllegalArgumentException("User not found");
                });
        user.setActive(false);
        repo.save(user);
        log.debug("User deactivated: id={}", id);
    }
}
