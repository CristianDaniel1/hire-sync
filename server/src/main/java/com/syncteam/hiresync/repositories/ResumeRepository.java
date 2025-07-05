package com.syncteam.hiresync.repositories;

import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    Optional<Resume> findByCandidate(Candidate candidate);

    boolean existsByCandidate(Candidate candidate);
}
