package com.syncteam.hiresync.mappers;

import com.syncteam.hiresync.dtos.companies.CompanyDto;
import com.syncteam.hiresync.dtos.companies.CompanyResponseDto;
import com.syncteam.hiresync.dtos.companies.UpdateCompanyDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    @Autowired
    PasswordEncoder encoder;

    @Mapping(target = "user", expression = "java(toAppUser(dto))")
    public abstract Company toEntity(CompanyDto dto);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "roles", source = "user.roles")
    public abstract CompanyResponseDto toDTO(Company company);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    public abstract void updateCompanyFromDto(CompanyDto dto, @MappingTarget Company entity);

    public Company partialUpdateCompanyFromDto(UpdateCompanyDto dto, Company company) {
        if (dto.name() != null) company.getUser().setName(dto.name());
        if (dto.email() != null) company.getUser().setEmail(dto.email());
        if (dto.password() != null) {
            String encodedPassword = encoder.encode(dto.password());
            company.getUser().setPassword(encodedPassword);
        }
        if (dto.cnpj() != null) company.setCnpj(dto.cnpj());
        if (dto.logoImage() != null) company.setLogoImage(dto.logoImage());
        if (dto.phone() != null) company.setPhone(dto.phone());
        if (dto.website() != null) company.setWebsite(dto.website());
        if (dto.address() != null) company.setAddress(dto.address());
        if (dto.isHeadOffice() != null) company.setIsHeadOffice(dto.isHeadOffice());
        if (dto.groupName() != null) company.setGroupName(dto.groupName());

        return company;
    }

    public AppUser toAppUser(CompanyDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}

