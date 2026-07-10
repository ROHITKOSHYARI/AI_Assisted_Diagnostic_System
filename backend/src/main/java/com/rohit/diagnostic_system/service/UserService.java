package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.CreateUserRequest;
import com.rohit.diagnostic_system.DTO.UserResponse;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(CreateUserRequest request){
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
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
        userRepository.save(user);
    }

    public UserResponse getUser(UUID id) throws UserPrincipalNotFoundException {
        try {
            User user = userRepository.getUserById(id);

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
        catch (Exception e){
            throw new UserPrincipalNotFoundException("User not found");
        }
    }

}
