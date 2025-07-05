package com.syncteam.hiresync.dtos.resumes;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResumeResponseDto(
        UUID id,
        String summary,
        String skills,
        String languages,
        String certifications,
        String githubUrl,
        String portfolioUrl,
        String linkedinUrl,
        String repositoryUrl,
        String fileUrl,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
