package com.syncteam.hiresync.repositories;

import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    Optional<Candidate> findByUser(AppUser user);
    boolean existsByCpf(String cpf);
}
