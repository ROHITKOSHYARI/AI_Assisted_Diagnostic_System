package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String bloodGroup;

    private Double height;

    private Double weight;

    private String address;

    private String profilePicture;

    private boolean verified;

    private boolean active;

    private LocalDateTime createdAt;
}

