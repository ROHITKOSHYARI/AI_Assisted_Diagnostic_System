package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.AvailabilityStatus;
import com.rohit.diagnostic_system.Enum.DoctorStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String specialization;

    private String qualification;

    private Integer experience;

    private String hospital;

    private String licenseNumber;

    private String profilePicture;

    private DoctorStatus status;

    private String rejectionReason;

    private Double consultationFee;

    private AvailabilityStatus availabilityStatus;

    private LocalDateTime createdAt;
}


