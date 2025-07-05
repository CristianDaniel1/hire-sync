package com.syncteam.hiresync.mappers;

import com.syncteam.hiresync.dtos.resumes.ResumeDto;
import com.syncteam.hiresync.dtos.resumes.ResumeResponseDto;
import com.syncteam.hiresync.entities.Resume;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    Resume toEntity(ResumeDto dto);

    @Mapping(source = "fileUrl", target = "fileUrl")
    ResumeResponseDto toResponseDto(Resume resume);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ResumeDto dto, @MappingTarget Resume resume);
}
