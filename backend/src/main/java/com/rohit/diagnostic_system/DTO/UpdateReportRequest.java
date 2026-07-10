package com.rohit.diagnostic_system.DTO;

import com.rohit.diagnostic_system.Enum.ReportStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReportRequest {

    private UUID doctorId;

    private String disease;

    private Double confidence;

    private String modelName;

    private String imageUrl;

    private String gradCamImageUrl;

    private String symptoms;

    private String geminiAnalysis;

    private ReportStatus status;
}


