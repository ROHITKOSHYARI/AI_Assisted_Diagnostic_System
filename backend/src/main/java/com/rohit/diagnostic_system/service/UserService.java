package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UpdateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse saveUser(CreateUserRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .bloodGroup(request.getBloodGroup())
                .height(request.getHeight())
                .weight(request.getWeight())
                .address(request.getAddress())
                .active(true)
                .verified(false)
                .build();
        User saved = userRepository.save(user);
        log.info("User created id={}", saved.getId());
        return toResponse(saved);
    }

    public UserResponse getUser(UUID id) {
        log.info("Fetching user id={}", id);
        return toResponse(findUser(id));
    }

    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = findUser(id);
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());
        if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getBloodGroup() != null) user.setBloodGroup(request.getBloodGroup());
        if (request.getHeight() != null) user.setHeight(request.getHeight());
        if (request.getWeight() != null) user.setWeight(request.getWeight());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getProfilePicture() != null) user.setProfilePicture(request.getProfilePicture());
        if (request.getVerified() != null) user.setVerified(request.getVerified());
        if (request.getActive() != null) user.setActive(request.getActive());
        User saved = userRepository.save(user);
        log.info("User updated id={}", saved.getId());
        return toResponse(saved);
    }

    public void deleteUser(UUID id) {
        User user = findUser(id);
        userRepository.delete(user);
        log.info("User deleted id={}", id);
    }

    private User findUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found id={}", id);
            return new NoSuchElementException("User not found");
        });
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .bloodGroup(user.getBloodGroup())
                .height(user.getHeight())
                .weight(user.getWeight())
                .address(user.getAddress())
                .profilePicture(user.getProfilePicture())
                .verified(user.isVerified())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
