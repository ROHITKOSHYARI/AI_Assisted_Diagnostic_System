package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UpdateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/save_user")
    public ResponseEntity<UserResponse> saveUser(@RequestBody CreateUserRequest user) {
        log.info("Create user requested for email={}", user.getEmail());
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        log.info("Get user requested id={}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Get all users requested");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update_user/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        log.info("Update user requested id={}", id);
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("Delete user requested id={}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
