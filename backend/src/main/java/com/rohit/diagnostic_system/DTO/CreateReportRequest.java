package com.rohit.diagnostic_system.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReportRequest {

    private UUID patientId;

    private UUID doctorId;

    private String disease;

    private Double confidence;

    private String modelName;

    private String imageUrl;

    private String gradCamImageUrl;

    private String symptoms;

    private String geminiAnalysis;
}


