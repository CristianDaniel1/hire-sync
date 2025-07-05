package com.syncteam.hiresync.mappers;

import com.syncteam.hiresync.dtos.jobs.JobDto;
import com.syncteam.hiresync.dtos.jobs.JobResponseDto;
import com.syncteam.hiresync.entities.Job;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "company", ignore = true)
    Job toEntity(JobDto dto);

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.user.name")
    @Mapping(target = "website", source = "company.website")
    @Mapping(target = "address", source = "company.address")
    @Mapping(target = "companyLogo", source = "company.logoImage")
    JobResponseDto toResponseDto(Job job);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "company", ignore = true)
    void updateFromDto(JobDto dto, @MappingTarget Job job);
}
