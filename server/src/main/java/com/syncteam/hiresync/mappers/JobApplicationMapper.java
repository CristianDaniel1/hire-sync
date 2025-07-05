package com.syncteam.hiresync.mappers;

import com.syncteam.hiresync.dtos.jobApplications.JobApplicationDto;
import com.syncteam.hiresync.dtos.jobApplications.JobApplicationResponseDto;
import com.syncteam.hiresync.entities.JobApplication;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "job", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    JobApplication toEntity(JobApplicationDto dto);

    @Mapping(target = "candidateId", source = "candidate.id")
    @Mapping(target = "candidateName", source = "candidate.user.name")
    @Mapping(target = "candidateEmail", source = "candidate.user.email")
    @Mapping(target = "jobId", source = "job.id")
    @Mapping(target = "jobTitle", source = "job.title")
    @Mapping(target = "companyName", source = "job.company.user.name")
    JobApplicationResponseDto toResponseDto(JobApplication entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "job", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    void updateFromDto(JobApplicationDto dto, @MappingTarget JobApplication entity);
}


