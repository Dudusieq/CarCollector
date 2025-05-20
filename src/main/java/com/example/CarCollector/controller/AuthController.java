package com.example.CarCollector.controller;

import com.example.CarCollector.dto.AuthResponseDTO;
import com.example.CarCollector.dto.LoginRequestDTO;
import com.example.CarCollector.dto.RefreshTokenRequestDTO;
import com.example.CarCollector.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService svc;
    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        AuthResponseDTO dto = svc.login(req);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO req) {
        String newAccess = svc.refresh(req);
        return ResponseEntity.ok(new AuthResponseDTO(newAccess, null));
    }
}
    // dla wygenerowania hasla
    /*@GetMapping("/debug/encode")
    public String encode(@RequestParam String p) {
        return passwordEncoder.encode(p);
    }*/

