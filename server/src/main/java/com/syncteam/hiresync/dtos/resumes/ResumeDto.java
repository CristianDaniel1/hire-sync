package com.syncteam.hiresync.dtos.resumes;

public record ResumeDto(
        String summary,
        String skills,
        String languages,
        String certifications,
        String githubUrl,
        String portfolioUrl,
        String linkedinUrl,
        String repositoryUrl
) {}
