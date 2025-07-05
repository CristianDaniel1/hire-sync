package com.syncteam.hiresync.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record JobReportDto(
        UUID id,
        String title,
        String location,
        LocalDate expiresAt,
        Long totalApplications
) {}
