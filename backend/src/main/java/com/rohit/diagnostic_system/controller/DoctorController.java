package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.CreateDoctorRequest;
import com.rohit.diagnostic_system.DTO.DoctorResponse;
import com.rohit.diagnostic_system.DTO.UpdateDoctorRequest;
import com.rohit.diagnostic_system.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create_doctor")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody CreateDoctorRequest request) {
        log.info("Create doctor requested for email={}", request.getEmail());
        return new ResponseEntity<>(doctorService.createDoctor(request), HttpStatus.CREATED);
    }

    @GetMapping("/get_doctor/{id}")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable UUID id) {
        log.info("Get doctor requested id={}", id);
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping("/get_doctor")
    public ResponseEntity<DoctorResponse> getCurrentDoctor(){
        log.info("get doctor request detail by the current doctor");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        DoctorResponse doctorResponse = doctorService.getDoctor(email);
        return ResponseEntity.ok(doctorResponse);
    }

    @GetMapping("/get_all_doctors")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        log.info("Get all doctors requested");
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/update_doctor")
    public ResponseEntity<DoctorResponse> updateDoctor(@RequestBody UpdateDoctorRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Update doctor requested email={}", email);
        return ResponseEntity.ok(doctorService.updateDoctor(email, request));
    }

    @DeleteMapping("/delete_doctor")
    public ResponseEntity<Void> deleteDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        log.info("Delete doctor requested email={}", userEmail);
        doctorService.deleteDoctor(userEmail);
        return ResponseEntity.noContent().build();
    }
}
