package com.rohit.diagnostic_system.entity;

import com.rohit.diagnostic_system.Enum.AvailabilityStatus;
import com.rohit.diagnostic_system.Enum.DoctorStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    private String specialization;

    private String qualification;

    private Integer experience;

    private String hospital;

    private String licenseNumber;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private DoctorStatus status;

    private String rejectionReason;

    private Double consultationFee;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
