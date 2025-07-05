package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.jobs.JobDto;
import com.syncteam.hiresync.dtos.JobReportDto;
import com.syncteam.hiresync.dtos.jobs.JobResponseDto;
import com.syncteam.hiresync.dtos.PageResponseDto;
import com.syncteam.hiresync.entities.enums.WorkMode;
import com.syncteam.hiresync.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    /**
     * Lista vagas com filtros opcionais (título, localidade e Modelo de trabalho) e paginação.
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<JobResponseDto>> getAllJobs(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "workMode", required = false) WorkMode workMode,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit) {

        PageResponseDto<JobResponseDto> jobs = jobService
                .findAllJobs(title, location, workMode, page, limit);
        return ResponseEntity.ok(jobs);
    }

    /**
     * Retorna as vagas da empresa de acordo com o seu ID.
     * Acesso restrito a usuários com ROLE_COMPANY.
     */
    @GetMapping("/company/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<List<JobResponseDto>> getJobsByCompany(@PathVariable("id") UUID id) {
        List<JobResponseDto> jobs = jobService.findJobsByCompany(id);
        return ResponseEntity.ok(jobs);
    }

    /**
     * Busca uma vaga específica pelo ID.
     */
    @GetMapping("{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable("id") UUID id) {
        JobResponseDto job = jobService.findJobById(id);
        return ResponseEntity.ok(job);
    }

    /**
     * Cria uma nova vaga.
     * Acesso restrito a COMPANY.
     */
    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<JobResponseDto> createJob(@RequestBody @Valid JobDto dto) {
        JobResponseDto createdJob = jobService.createJob(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    /**
     * Atualiza uma vaga existente.
     * Acesso restrito a COMPANY.
     */
    @PutMapping("{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<JobResponseDto> updateJob(
            @PathVariable("id") UUID id,
            @RequestBody @Valid JobDto dto
    ) {
        JobResponseDto updatedJob = jobService.updateJob(id, dto);
        return ResponseEntity.ok(updatedJob);
    }

    /**
     * Deleta uma vaga.
     * Acesso restrito a COMPANY ou ADMIN.
     * @param id UUID da vaga
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") UUID id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna relatório de vagas de uma empresa.
     * @param id UUID da empresa
     */
    @GetMapping("/report/company/{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    public ResponseEntity<List<JobReportDto>> reportByCompany(@PathVariable("id") UUID id) {
        List<JobReportDto> reportByCompany = jobService.getReportByCompany(id);
        return ResponseEntity.ok(reportByCompany);
    }

    /**
     * Retorna relatório de vagas agrupadas por grupo.
     * @param group Nome do grupo
     */
    @GetMapping("/report/group/{group}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    public ResponseEntity<List<JobReportDto>> reportByGroup(@PathVariable("group") String group) {
        List<JobReportDto> reportByGroup = jobService.getReportByGroup(group);
        return ResponseEntity.ok(reportByGroup);
    }
}
