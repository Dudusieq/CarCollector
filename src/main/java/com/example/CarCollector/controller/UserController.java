package com.example.CarCollector.controller;

import com.example.CarCollector.dto.UserDTO;
import com.example.CarCollector.dto.UserResponseDTO;
import com.example.CarCollector.dto.UserUpdateDTO;
import com.example.CarCollector.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService svc;

    public UserController(UserService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserDTO dto) {
        UserResponseDTO created = svc.createUser(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        List<UserResponseDTO> users = svc.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getOne(@PathVariable Long id) {
        UserResponseDTO dto = svc.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDTO dto) {
        UserResponseDTO updated = svc.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}