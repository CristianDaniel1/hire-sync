package com.syncteam.hiresync.dtos.companies;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.UUID;

public record CompanyDto(
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

        @NotBlank(message = "CNPJ is required")
        @Size(min = 14, max = 14, message = "CNPJ must be exactly 14 digits")
        String cnpj,

        @Size(max = 200, message = "logo Image must be at most 100 characters")
        String logoImage,

        @Size(max = 15, message = "Phone must be at most 15 characters")
        String phone,

        @Size(max = 150, message = "Website must be at most 150 characters")
        String website,

        @NotBlank(message = "address is required")
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,

        @NotNull(message = "isHeadOffice is required")
        Boolean isHeadOffice,

        @NotNull(message = "groupName is required")
        @Size(max = 100, message = "groupName must be at most 100 characters")
        String groupName) {
}
