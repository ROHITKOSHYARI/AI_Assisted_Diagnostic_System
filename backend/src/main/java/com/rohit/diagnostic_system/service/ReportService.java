package com.rohit.diagnostic_system.service;

import com.rohit.diagnostic_system.DTO.CreateReportRequest;
import com.rohit.diagnostic_system.DTO.ReportResponse;
import com.rohit.diagnostic_system.DTO.UpdateReportRequest;
import com.rohit.diagnostic_system.Enum.ReportStatus;
import com.rohit.diagnostic_system.entity.Doctor;
import com.rohit.diagnostic_system.entity.Report;
import com.rohit.diagnostic_system.entity.User;
import com.rohit.diagnostic_system.repository.DoctorRepository;
import com.rohit.diagnostic_system.repository.ReportRepository;
import com.rohit.diagnostic_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public ReportResponse createReport(CreateReportRequest request) {
        User patient = userRepository.findById(request.getPatientId()).orElseThrow(() -> {
            log.warn("Report create failed: patient not found patientId={}", request.getPatientId());
            return new NoSuchElementException("Patient not found");
        });
        Doctor doctor = request.getDoctorId() == null ? null : doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> {
            log.warn("Report create failed: doctor not found doctorId={}", request.getDoctorId());
            return new NoSuchElementException("Doctor not found");
        });

        Report report = Report.builder()
                .patient(patient)
                .doctor(doctor)
                .disease(request.getDisease())
                .confidence(request.getConfidence())
                .modelName(request.getModelName())
                .imageUrl(request.getImageUrl())
                .gradCamImageUrl(request.getGradCamImageUrl())
                .symptoms(request.getSymptoms())
                .geminiAnalysis(request.getGeminiAnalysis())
                .status(ReportStatus.GENERATED)
                .createdAt(LocalDateTime.now())
                .build();
        Report saved = reportRepository.save(report);
        log.info("Report created id={} patientId={} doctorId={} status={}", saved.getId(), patient.getId(), doctor == null ? null : doctor.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public ReportResponse getReport(UUID id) {
        log.info("Fetching report id={}", id);
        return toResponse(findReport(id));
    }

    public List<ReportResponse> getAllReports() {
        log.info("Fetching all reports");
        return reportRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReportResponse> getReportsByPatient(UUID patientId) {
        log.info("Fetching reports by patientId={}", patientId);
        return reportRepository.findByPatientId(patientId).stream().map(this::toResponse).toList();
    }

    public List<ReportResponse> getReportsByDoctor(UUID doctorId) {
        log.info("Fetching reports by doctorId={}", doctorId);
        return reportRepository.findByDoctorId(doctorId).stream().map(this::toResponse).toList();
    }

    public ReportResponse updateReport(UUID id, UpdateReportRequest request) {
        Report report = findReport(id);
        if (request.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> {
                log.warn("Report update failed: doctor not found doctorId={}", request.getDoctorId());
                return new NoSuchElementException("Doctor not found");
            });
            report.setDoctor(doctor);
        }
        if (request.getDisease() != null) report.setDisease(request.getDisease());
        if (request.getConfidence() != null) report.setConfidence(request.getConfidence());
        if (request.getModelName() != null) report.setModelName(request.getModelName());
        if (request.getImageUrl() != null) report.setImageUrl(request.getImageUrl());
        if (request.getGradCamImageUrl() != null) report.setGradCamImageUrl(request.getGradCamImageUrl());
        if (request.getSymptoms() != null) report.setSymptoms(request.getSymptoms());
        if (request.getGeminiAnalysis() != null) report.setGeminiAnalysis(request.getGeminiAnalysis());
        if (request.getStatus() != null) report.setStatus(request.getStatus());
        Report saved = reportRepository.save(report);
        log.info("Report updated id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public void deleteReport(UUID id) {
        Report report = findReport(id);
        reportRepository.delete(report);
        log.info("Report deleted id={}", id);
    }

    private Report findReport(UUID id) {
        return reportRepository.findById(id).orElseThrow(() -> {
            log.warn("Report not found id={}", id);
            return new NoSuchElementException("Report not found");
        });
    }

    private ReportResponse toResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .patientId(report.getPatient().getId())
                .doctorId(report.getDoctor() == null ? null : report.getDoctor().getId())
                .disease(report.getDisease())
                .confidence(report.getConfidence())
                .modelName(report.getModelName())
                .imageUrl(report.getImageUrl())
                .gradCamImageUrl(report.getGradCamImageUrl())
                .symptoms(report.getSymptoms())
                .geminiAnalysis(report.getGeminiAnalysis())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
