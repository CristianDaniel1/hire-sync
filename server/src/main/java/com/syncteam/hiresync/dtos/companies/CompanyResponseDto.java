package com.syncteam.hiresync.dtos.companies;

import com.syncteam.hiresync.entities.enums.Role;

import java.util.Set;
import java.util.UUID;

public record CompanyResponseDto(
        UUID id,
        String name,
        String email,
        String cnpj,
        String logoImage,
        String phone,
        String website,
        String address,
        Boolean isHeadOffice,
        String groupName,
        Set<Role> roles) {
}
