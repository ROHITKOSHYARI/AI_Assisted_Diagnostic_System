package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.AuthResponse;
import com.rohit.diagnostic_system.DTO.LoginRequest;
import com.rohit.diagnostic_system.Enum.DoctorStatus;
import com.rohit.diagnostic_system.entity.Doctor;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.repository.DoctorRepository;
import com.rohit.diagnostic_system.repository.UserRepository;
import com.rohit.diagnostic_system.security.BlacklistService;
import com.rohit.diagnostic_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final BlacklistService blacklistService;

    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("User login failed: email not found email={}", request.getEmail());
                    return new BadCredentialsException("Invalid email or password");
                });

        if (!user.isActive() || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            log.warn("User login failed: inactive account or bad password email={}", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        if (blacklistService.isUserBlacklisted(user.getId(), user.getEmail())) {
            log.warn("User login failed: blacklisted userId={}", user.getId());
            throw new BadCredentialsException("Invalid email or password");
        }

        log.info("User login successful userId={}", user.getId());
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getId(), "PATIENT"));
    }

    public AuthResponse loginDoctor(LoginRequest request) {
        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Doctor login failed: email not found email={}", request.getEmail());
                    return new BadCredentialsException("Invalid email or password");
                });

        if (doctor.getStatus() != DoctorStatus.APPROVED || !passwordEncoder.matches(request.getPassword(), doctor.getPasswordHash())) {
            log.warn("Doctor login failed: status={} or bad password email={}", doctor.getStatus(), request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        log.info("Doctor login successful doctorId={}", doctor.getId());
        return new AuthResponse(jwtService.generateToken(doctor.getEmail(), doctor.getId(), "DOCTOR"));
    }
}
