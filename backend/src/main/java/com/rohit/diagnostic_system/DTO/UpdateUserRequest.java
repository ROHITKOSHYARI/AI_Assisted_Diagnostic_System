package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

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

    private Boolean verified;

    private Boolean active;
}


