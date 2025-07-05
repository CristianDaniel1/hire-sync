package com.syncteam.hiresync.repositories;

import com.syncteam.hiresync.dtos.JobReportDto;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>, JpaSpecificationExecutor<Job> {
    List<Job> findAllByCompany(Company company);

    @Query("""
        SELECT new com.syncteam.hiresync.dtos.JobReportDto(
            j.id, j.title, j.location, j.expiresAt, COUNT(ja.id)
        )
        FROM Job j
        LEFT JOIN JobApplication ja ON ja.job = j
        WHERE j.company = :company
        GROUP BY j.id
    """)
    List<JobReportDto> findJobReportsByCompany(@Param("company") Company company);

    @Query("""
        SELECT new com.syncteam.hiresync.dtos.JobReportDto(
            j.id, j.title, j.location, j.expiresAt, COUNT(ja.id)
        )
        FROM Job j
        LEFT JOIN JobApplication ja ON ja.job = j
        WHERE j.company.groupName = :groupName
        GROUP BY j.id
    """)
    List<JobReportDto> findJobReportsByGroup(@Param("groupName") String groupName);
}
