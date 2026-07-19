package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UpdateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.repository.UserRepository;
import com.rohit.diagnostic_system.security.BlacklistService;
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
    private final BlacklistService blacklistService;

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

    public UserResponse getUser(String email) {
        log.info("Fetching user");
        return toResponse(findUser(email));
    }

    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse updateUser(String email, UpdateUserRequest request) {
        User user = findUser(email);
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
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        User saved = userRepository.save(user);

        if (request.getActive() != null && !request.getActive()) {
            blacklistService.blacklistUser(saved.getId(), saved.getEmail());
        } else if (request.getActive() != null) {
            blacklistService.removeUserFromBlacklist(saved.getId(), saved.getEmail());
        }

        log.info("User updated id={}", saved.getId());
        return toResponse(saved);
    }

    public void deleteUser(String email) {
        User user = findUser(email);
        blacklistService.blacklistUser(user.getId(), user.getEmail());
        userRepository.delete(user);
        log.info("User deleted");
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.warn("User not found");
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
