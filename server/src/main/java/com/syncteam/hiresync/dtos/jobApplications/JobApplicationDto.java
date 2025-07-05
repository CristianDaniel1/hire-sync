package com.syncteam.hiresync.dtos.jobApplications;

import com.syncteam.hiresync.entities.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JobApplicationDto(
        @NotNull(message = "Job ID is required")
        UUID jobId,

        @NotNull(message = "Status is required")
        Status status
) {}
