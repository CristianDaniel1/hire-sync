package com.syncteam.hiresync.entities;

import com.syncteam.hiresync.entities.enums.Contract;
import com.syncteam.hiresync.entities.enums.WorkMode;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "jobs", schema = "public")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "salary",  precision = 18, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode", length = 50, nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", length = 50, nullable = false)
    private Contract contractType;

    @Column(name = "requirements", columnDefinition = "TEXT", nullable = false)
    private String requirements;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities;

    @Column(name = "course_required", length = 100)
    private String courseRequired;

    @Column(name = "graduation_year_required")
    private LocalDate graduationYearRequired;

    @Column(name = "min_semester")
    private Integer minSemester;

    @Column(name = "expires_at", nullable = false)
    private LocalDate expiresAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Job() {}
}
