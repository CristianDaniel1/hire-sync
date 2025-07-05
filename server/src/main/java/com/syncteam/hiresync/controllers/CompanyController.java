package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.companies.CompanyResponseDto;
import com.syncteam.hiresync.dtos.companies.UpdateCompanyDto;
import com.syncteam.hiresync.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    /**
     * Endpoint para retornar todas as empresas cadastradas.
     * @return lista de empresas no formato DTO
     */
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanies() {
        List<CompanyResponseDto> companies = service.findAllCompanies();
        return ResponseEntity.ok(companies);
    }

    /**
     * Endpoint para obter uma empresa específica por ID.
     * @param id UUID da empresa
     * @return DTO com os dados da empresa encontrada
     */
    @GetMapping("{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") UUID id) {
        CompanyResponseDto company = service.findCompanyById(id);
        return ResponseEntity.ok(company);
    }

    /**
     * Endpoint para listar empresas com base no nome do grupo.
     * @param group nome do grupo (ex: "tech group", "coca-cola company", etc.)
     * @return lista de empresas que pertencem ao grupo
     */
    @GetMapping("group/{group}")
    public ResponseEntity<List<CompanyResponseDto>> getCompaniesByGroup(@PathVariable("group") String group) {
        List<CompanyResponseDto> companies = service.findCompaniesByGroup(group);
        return ResponseEntity.ok(companies);
    }

    /**
     * Endpoint para atualizar dados de uma empresa.
     * Requer papel COMPANY ou ADMIN.
     * @param id UUID da empresa a ser atualizada
     * @param dto dados atualizados da empresa
     * @return DTO com as informações da empresa após atualização
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    public ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable("id") UUID id, @RequestBody @Valid UpdateCompanyDto dto) {
        CompanyResponseDto updatedCompany = service.updateCompany(id, dto);
        return ResponseEntity.ok(updatedCompany);
    }

    /**
     * Endpoint para deletar uma empresa com base no ID.
     * Requer papel COMPANY ou ADMIN.
     * @param id UUID da empresa a ser removida
     * @return DTO com os dados da empresa excluída
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    public ResponseEntity<CompanyResponseDto> deleteCompany(@PathVariable("id") UUID id) {
        CompanyResponseDto deletedCompany = service.deleteCompany(id);
        return ResponseEntity.ok(deletedCompany);
    }
}
