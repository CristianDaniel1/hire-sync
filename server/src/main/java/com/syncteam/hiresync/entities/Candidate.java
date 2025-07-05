package com.syncteam.hiresync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "candidates", schema = "public")
@Getter
@Setter
public class Candidate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cpf", length = 15, nullable = false, unique = true)
    private String cpf;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "course", length = 100, nullable = false)
    private String course;

    @Column(name = "currently_studying")
    private boolean currentlyStudying;

    @Column(name = "current_semester")
    private int currentSemester;

    @Column(name = "graduation_year")
    private LocalDate graduationYear;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Resume resume;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    public Candidate() {}
}
