package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.CreateDoctorRequest;
import com.rohit.diagnostic_system.DTO.DoctorResponse;
import com.rohit.diagnostic_system.DTO.UpdateDoctorRequest;
import com.rohit.diagnostic_system.Enum.AvailabilityStatus;
import com.rohit.diagnostic_system.Enum.DoctorStatus;
import com.rohit.diagnostic_system.entity.Doctor;
import com.rohit.diagnostic_system.repository.DoctorRepository;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorResponse createDoctor(CreateDoctorRequest request) {
        Doctor doctor = Doctor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .experience(request.getExperience())
                .hospital(request.getHospital())
                .licenseNumber(request.getLicenseNumber())
                .consultationFee(request.getConsultationFee())
                .status(DoctorStatus.PENDING)
                .availabilityStatus(AvailabilityStatus.OFFLINE)
                .build();
        Doctor saved = doctorRepository.save(doctor);
        log.info("Doctor created id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public DoctorResponse getDoctor(String email) {
        log.info("Fetching doctor email={}", email);
        return toResponse(findDoctor(email));
    }

    public DoctorResponse getDoctorById(UUID id) {
        log.info("Fetching doctor id={}", id);
        return toResponse(findDoctorById(id));
    }

    public List<DoctorResponse> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DoctorResponse updateDoctor(String email, UpdateDoctorRequest request) {
        Doctor doctor = findDoctor(email);
        if (request.getFirstName() != null) doctor.setFirstName(request.getFirstName());
        if (request.getLastName() != null) doctor.setLastName(request.getLastName());
        if (request.getEmail() != null) doctor.setEmail(request.getEmail());
        if (request.getSpecialization() != null) doctor.setSpecialization(request.getSpecialization());
        if (request.getQualification() != null) doctor.setQualification(request.getQualification());
        if (request.getExperience() != null) doctor.setExperience(request.getExperience());
        if (request.getHospital() != null) doctor.setHospital(request.getHospital());
        if (request.getLicenseNumber() != null) doctor.setLicenseNumber(request.getLicenseNumber());
        if (request.getProfilePicture() != null) doctor.setProfilePicture(request.getProfilePicture());
        if (request.getStatus() != null) doctor.setStatus(request.getStatus());
        if (request.getRejectionReason() != null) doctor.setRejectionReason(request.getRejectionReason());
        if (request.getConsultationFee() != null) doctor.setConsultationFee(request.getConsultationFee());
        if (request.getAvailabilityStatus() != null) doctor.setAvailabilityStatus(request.getAvailabilityStatus());
        Doctor saved = doctorRepository.save(doctor);
        log.info("Doctor updated id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public void deleteDoctor(String email) {
        Doctor doctor = findDoctor(email);
        doctorRepository.delete(doctor);
        log.info("Doctor deleted email={}", email);
    }

    private Doctor findDoctor(String email) {
        return doctorRepository.findByEmail(email).orElseThrow(() -> {
            log.warn("Doctor not found email={}", email);
            return new NoSuchElementException("Doctor not found");
        });
    }

    private Doctor findDoctorById(UUID id) {
        return doctorRepository.findById(id).orElseThrow(() -> {
            log.warn("Doctor not found id={}", id);
            return new NoSuchElementException("Doctor not found");
        });
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .specialization(doctor.getSpecialization())
                .qualification(doctor.getQualification())
                .experience(doctor.getExperience())
                .hospital(doctor.getHospital())
                .licenseNumber(doctor.getLicenseNumber())
                .profilePicture(doctor.getProfilePicture())
                .status(doctor.getStatus())
                .rejectionReason(doctor.getRejectionReason())
                .consultationFee(doctor.getConsultationFee())
                .availabilityStatus(doctor.getAvailabilityStatus())
                .createdAt(doctor.getCreatedAt())
                .build();
    }
}
