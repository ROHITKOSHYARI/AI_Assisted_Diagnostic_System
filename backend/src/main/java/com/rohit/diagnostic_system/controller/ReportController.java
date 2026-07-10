package com.rohit.diagnostic_system.controller;

import com.rohit.diagnostic_system.DTO.CreateReportRequest;
import com.rohit.diagnostic_system.DTO.ReportResponse;
import com.rohit.diagnostic_system.DTO.UpdateReportRequest;
import com.rohit.diagnostic_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create_report")
    public ResponseEntity<ReportResponse> createReport(@RequestBody CreateReportRequest request) {
        log.info("Create report requested patientId={} doctorId={}", request.getPatientId(), request.getDoctorId());
        return new ResponseEntity<>(reportService.createReport(request), HttpStatus.CREATED);
    }

    @GetMapping("/get_report/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable UUID id) {
        log.info("Get report requested id={}", id);
        return ResponseEntity.ok(reportService.getReport(id));
    }

    @GetMapping("/get_all_reports")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        log.info("Get all reports requested");
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/get_reports_by_patient/{patientId}")
    public ResponseEntity<List<ReportResponse>> getReportsByPatient(@PathVariable UUID patientId) {
        log.info("Get reports by patient requested patientId={}", patientId);
        return ResponseEntity.ok(reportService.getReportsByPatient(patientId));
    }

    @GetMapping("/get_reports_by_doctor/{doctorId}")
    public ResponseEntity<List<ReportResponse>> getReportsByDoctor(@PathVariable UUID doctorId) {
        log.info("Get reports by doctor requested doctorId={}", doctorId);
        return ResponseEntity.ok(reportService.getReportsByDoctor(doctorId));
    }

    @PutMapping("/update_report/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable UUID id, @RequestBody UpdateReportRequest request) {
        log.info("Update report requested id={}", id);
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @DeleteMapping("/delete_report/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable UUID id) {
        log.info("Delete report requested id={}", id);
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
