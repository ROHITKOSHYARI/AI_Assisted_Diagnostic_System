package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/save_user")
    public ResponseEntity<String> saveUser(@RequestBody CreateUserRequest user){
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get_user/{ID}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID ID) throws UserPrincipalNotFoundException {
        UserResponse user = userService.getUser(ID);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
