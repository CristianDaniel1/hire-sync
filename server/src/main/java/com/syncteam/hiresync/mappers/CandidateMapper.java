package com.syncteam.hiresync.mappers;

import com.syncteam.hiresync.dtos.candidates.CandidateDto;
import com.syncteam.hiresync.dtos.candidates.CandidateResponseDto;
import com.syncteam.hiresync.dtos.candidates.UpdateCandidateDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class CandidateMapper {

    @Autowired
    PasswordEncoder encoder;

    @Mapping(target = "user", expression = "java(toAppUser(dto))")
    public abstract Candidate toEntity(CandidateDto dto);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "roles", source = "user.roles")
    public abstract CandidateResponseDto toDTO(Candidate candidate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    public abstract void updateCandidateFromDto(CandidateDto dto, @MappingTarget Candidate entity);

    public Candidate partialUpdateCandidateFromDto(UpdateCandidateDto dto, Candidate candidate) {
        if (dto.name() != null) candidate.getUser().setName(dto.name());
        if (dto.email() != null) candidate.getUser().setEmail(dto.email());
        if (dto.password() != null) {
            String encodedPassword = encoder.encode(dto.password());
            candidate.getUser().setPassword(encodedPassword);
        }
        if (dto.cpf() != null) candidate.setCpf(dto.cpf());
        if (dto.course() != null) candidate.setCourse(dto.course());
        if (dto.birthDate() != null) candidate.setBirthDate(dto.birthDate());
        if (dto.address() != null) candidate.setAddress(dto.address());
        if (dto.currentlyStudying() != null) candidate.setCurrentlyStudying(dto.currentlyStudying());
        if (dto.currentSemester() != null) candidate.setCurrentSemester(dto.currentSemester());
        if (dto.graduationYear() != null) candidate.setGraduationYear(dto.graduationYear());
        if (dto.gender() != null) candidate.setGender(dto.gender());
        if (dto.phone() != null) candidate.setPhone(dto.phone());

        return candidate;
    }

    public AppUser toAppUser(CandidateDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}
