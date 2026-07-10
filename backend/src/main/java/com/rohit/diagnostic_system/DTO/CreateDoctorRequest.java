package com.rohit.diagnostic_system.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDoctorRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String specialization;

    private String qualification;

    private Integer experience;

    private String hospital;

    private String licenseNumber;

    private Double consultationFee;
}


