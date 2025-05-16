package com.example.CarCollector.controller;

import com.example.CarCollector.dto.UserDTO;
import com.example.CarCollector.dto.UserResponseDTO;
import com.example.CarCollector.dto.UserUpdateDTO;
import com.example.CarCollector.map.UserMapper;
import com.example.CarCollector.model.User;
import com.example.CarCollector.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class    UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
        // tworzenie uzytkownika
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserDTO dto) {
        User created = userService.createUser(dto);
        return ResponseEntity.ok(userMapper.toDto(created));
    }
        // wyswietlanie wszystkich uzytkownikow
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        List<User> all = userService.getAllUsers();
        List<UserResponseDTO> dtos = all.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
        // wyswietlanie jednego uzytkownika
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getOne(@PathVariable Long id) {
        User u = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toDto(u));
    }
        // aktualizowanie uzytkownika
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDTO dto) {
        User u = userService.updateUser(id, dto);
        return ResponseEntity.ok(userMapper.toDto(u));
    }
    // usuwanie uzytkownika
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
