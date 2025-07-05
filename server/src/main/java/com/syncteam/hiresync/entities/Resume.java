package com.syncteam.hiresync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resumes", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;

    @Column(name = "languages", columnDefinition = "TEXT")
    private String languages;

    @Column(name = "certifications", columnDefinition = "TEXT")
    private String certifications;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "repository_url")
    private String repositoryUrl;

    @Column(name = "file_url")
    private String fileUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    private Candidate candidate;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
