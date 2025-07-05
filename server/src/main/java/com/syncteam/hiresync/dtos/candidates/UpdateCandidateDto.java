package com.syncteam.hiresync.dtos.candidates;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UpdateCandidateDto(
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,
        @CPF(message = "invalid CPF")
        String cpf,
        String course,
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,
        Boolean currentlyStudying,
        Integer currentSemester,
        LocalDate graduationYear,
        String gender,
        String phone) {
}
