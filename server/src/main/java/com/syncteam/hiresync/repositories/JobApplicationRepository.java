package com.syncteam.hiresync.repositories;

import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Job;
import com.syncteam.hiresync.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    boolean existsByJobAndCandidate(Job job, Candidate candidate);

    List<JobApplication> findAllByCandidate(Candidate candidate);

    List<JobApplication> findAllByJobIn(List<Job> jobs);

    List<JobApplication> findAllByJob(Job job);
}