package com.syncteam.hiresync.dtos.jobs;

import com.syncteam.hiresync.entities.enums.Contract;
import com.syncteam.hiresync.entities.enums.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobResponseDto(
        UUID id,
        String title,
        String description,
        String location,
        BigDecimal salary,
        WorkMode workMode,
        Contract contractType,
        String requirements,
        String responsibilities,
        String courseRequired,
        LocalDate graduationYearRequired,
        Integer minSemester,
        LocalDate expiresAt,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,

        UUID companyId,
        String companyName,
        String website,
        String companyLogo,
        String address
) {}
