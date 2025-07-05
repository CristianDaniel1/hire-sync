package com.syncteam.hiresync.dtos.jobApplications;

import com.syncteam.hiresync.entities.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record JobApplicationResponseDto(
        UUID id,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,

        UUID candidateId,
        String candidateName,
        String candidateEmail,

        UUID jobId,
        String jobTitle,
        String companyName
) {}
