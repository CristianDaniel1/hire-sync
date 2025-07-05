package com.syncteam.hiresync.dtos.candidates;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record CandidateDto(
        UUID id,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "CPF is required")
//        @CPF(message = "invalid CPF") retirado no momento para facilitar criação
        String cpf,

        @NotBlank(message = "Course is required")
        @Size(max = 100, message = "Course must be at most 100 characters")
        String course,

        @NotNull(message = "birthDate is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotBlank(message = "address is required")
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,

        boolean currentlyStudying,
        int currentSemester,
        LocalDate graduationYear,
        String gender,
        String phone) {
}
