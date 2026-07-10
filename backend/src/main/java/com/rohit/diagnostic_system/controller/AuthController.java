package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.AuthResponse;
import com.rohit.diagnostic_system.DTO.LoginRequest;
import com.rohit.diagnostic_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        log.info("User login requested for email={}", request.getEmail());
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<AuthResponse> loginDoctor(@RequestBody LoginRequest request) {
        log.info("Doctor login requested for email={}", request.getEmail());
        return ResponseEntity.ok(authService.loginDoctor(request));
    }
}
