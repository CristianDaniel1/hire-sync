package com.syncteam.hiresync.dtos.candidates;

import com.syncteam.hiresync.entities.enums.Role;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record CandidateResponseDto(
        UUID id,
        String name,
        String email,
        String course,
        String address,
        String cpf,
        LocalDate birthDate,
        LocalDate graduationYear,
        boolean currentlyStudying,
        int currentSemester,
        String gender,
        String phone,
        Set<Role> roles) {
}
