package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.Gender;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String bloodGroup;

    private Double height;

    private Double weight;

    private String address;
}
