package com.example.CarCollector.service;

import com.example.CarCollector.dto.UserDTO;
import com.example.CarCollector.dto.UserUpdateDTO;
import com.example.CarCollector.model.Role;
import com.example.CarCollector.model.User;
import com.example.CarCollector.repository.RoleRepository;
import com.example.CarCollector.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public User createUser(UserDTO dto) {
        log.info("Creating user: {}", dto.getUsername());
        // sprawdzenie czy nie juz takiego uzytkownika
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }

        // pobieranie roli
        Set<Role> roles = dto.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        // tworenie encji uzytkownika
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    // aktualizacja uzytkownika
    @Transactional
    public User updateUser(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        log.info("Updating user {} active={}", user.getUsername(), dto.getActive());
        user.setActive(dto.getActive());
        return userRepository.save(user);
    }

    // usuwanie uzytkownika poprzez ustawienie active na false
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        log.info("Deactivating user: {}", user.getUsername());
        user.setActive(false);
        userRepository.save(user);
    }

    // pobieranie uzytkownikow
    @Transactional(readOnly = true)
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // pobieranie jednego uzytkownika
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }
}
