package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UpdateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final SecurityFilterChain securityFilterChain;

    @PostMapping("/save_user")
    public ResponseEntity<UserResponse> saveUser(@RequestBody CreateUserRequest user) {
        log.info("Create user requested for email={}", user.getEmail());
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserResponse> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Get user requested : {}",userService.getUser(email).getFirstName());
        return ResponseEntity.ok(userService.getUser(email));
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Get all users requested");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update_user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Update user requested : {}",userService.getUser(email).getFirstName());
        return ResponseEntity.ok(userService.updateUser(email, request));
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Delete user requested {}",userService.getUser(email).getFirstName());
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
