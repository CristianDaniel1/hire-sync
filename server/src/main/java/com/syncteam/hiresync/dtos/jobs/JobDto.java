package com.syncteam.hiresync.dtos.jobs;

import com.syncteam.hiresync.entities.enums.Contract;
import com.syncteam.hiresync.entities.enums.WorkMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record JobDto(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be at most 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Location is required")
        @Size(max = 100, message = "Location must be at most 100 characters")
        String location,

        @PositiveOrZero(message = "Salary must be zero or positive")
        BigDecimal salary,

        @NotNull(message = "Work mode is required")
        WorkMode workMode,

        @NotNull(message = "Contract type is required")
        Contract contractType,

        @NotBlank(message = "Requirements are required")
        String requirements,

        String responsibilities,

        @Size(max = 100, message = "Course required must be at most 100 characters")
        String courseRequired,

        @PastOrPresent(message = "Graduation year must be current or past")
        LocalDate graduationYearRequired,

        @Min(value = 1, message = "Minimum semester must be at least 1")
        Integer minSemester,

        @NotNull(message = "Deadline is required")
        @FutureOrPresent(message = "Deadline must be in the present or future")
        LocalDate expiresAt
) {}
