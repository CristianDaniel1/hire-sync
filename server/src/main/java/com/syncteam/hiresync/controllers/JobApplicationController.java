package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.jobApplications.JobApplicationDto;
import com.syncteam.hiresync.dtos.jobApplications.JobApplicationResponseDto;
import com.syncteam.hiresync.services.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService applicationService;

    /**
     * Retorna todas as candidaturas registradas.
     * Acesso restrito a ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobApplicationResponseDto>> getAllApplications() {
        List<JobApplicationResponseDto> applications = applicationService.findAllApplications();
        return ResponseEntity.ok(applications);
    }

    /**
     * Retorna uma candidatura específica pelo seu ID.
     * Valida se o usuário atual tem permissão para visualizar.
     * @param id UUID da candidatura
     */
    @GetMapping("{id}")
    public ResponseEntity<JobApplicationResponseDto> getApplicationById(@PathVariable("id") UUID id) {
        JobApplicationResponseDto application = applicationService.findApplicationById(id);
        return ResponseEntity.ok(application);
    }

    /**
     * Retorna todas as candidaturas feitas por um candidato.
     * Acesso restrito a ADMIN ou ao próprio CANDIDATE.
     * @param candidateId UUID do candidato
     */
    @GetMapping("/candidate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE')")
    public ResponseEntity<List<JobApplicationResponseDto>> getApplicationsByCandidate(@PathVariable("id") UUID candidateId) {
        List<JobApplicationResponseDto> applications = applicationService.findApplicationsByCandidate(candidateId);
        return ResponseEntity.ok(applications);
    }

    /**
     * Retorna todas as candidaturas associadas a uma empresa.
     * Acesso restrito a ADMIN ou COMPANY.
     * @param companyId UUID da empresa
     */
    @GetMapping("/company/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    public ResponseEntity<List<JobApplicationResponseDto>> getApplicationsByCompany(@PathVariable("id") UUID companyId) {
        List<JobApplicationResponseDto> applications = applicationService.findApplicationsByCompany(companyId);
        return ResponseEntity.ok(applications);
    }

    /**
     * Retorna todas as candidaturas associadas a uma vaga.
     * Acesso restrito a ADMIN ou COMPANY.
     * @param jobId UUID da vaga
     */
    @GetMapping("/job/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    public ResponseEntity<List<JobApplicationResponseDto>> getApplicationsByJob(@PathVariable("id") UUID jobId) {
        List<JobApplicationResponseDto> applications = applicationService.findApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }

    /**
     * Cria uma nova candidatura para o candidato logado.
     * Acesso restrito ao perfil CANDIDATE.
     * @param dto dados da candidatura
     */
    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<JobApplicationResponseDto> createApplication(@RequestBody @Valid JobApplicationDto dto) {
        JobApplicationResponseDto application = applicationService.createApplication(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    /**
     * Atualiza os dados de uma candidatura existente.
     * Acesso restrito a ADMIN ou COMPANY.
     * @param id UUID da candidatura
     * @param dto novos dados da candidatura
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    public ResponseEntity<JobApplicationResponseDto> updateApplication(
            @PathVariable("id") UUID id,
            @RequestBody @Valid JobApplicationDto dto
    ) {
        JobApplicationResponseDto updatedApplication = applicationService.updateApplication(id, dto);
        return ResponseEntity.ok(updatedApplication);
    }

    /**
     * Remove uma candidatura com base no ID.
     * @param id UUID da candidatura
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") UUID id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
