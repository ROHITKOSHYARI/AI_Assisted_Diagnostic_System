package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.AvailabilityStatus;
import com.rohit.diagnostic_system.Enum.DoctorStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDoctorRequest {

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
}


