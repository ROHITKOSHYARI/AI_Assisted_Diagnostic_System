package com.rohit.diagnostic_system.repository;

import com.rohit.diagnostic_system.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByPatientId(UUID patientId);

    List<Report> findByDoctorId(UUID doctorId);
}


