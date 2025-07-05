package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.JobReportDto;
import com.syncteam.hiresync.dtos.PageResponseDto;
import com.syncteam.hiresync.dtos.jobs.JobDto;
import com.syncteam.hiresync.dtos.jobs.JobResponseDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.entities.Job;
import com.syncteam.hiresync.entities.enums.WorkMode;
import com.syncteam.hiresync.exceptions.NotAllowedException;
import com.syncteam.hiresync.exceptions.NotFoundException;
import com.syncteam.hiresync.mappers.JobMapper;
import com.syncteam.hiresync.repositories.JobRepository;
import com.syncteam.hiresync.repositories.specs.JobSpecs;
import com.syncteam.hiresync.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository repository;
    private final CompanyService companyService;
    private final JobMapper mapper;

    private final SecurityService securityService;

    /**
     * Busca todas as vagas com filtros opcionais (título e localidade) e paginação.
     *
     * @param title    Filtro por título da vaga (opcional)
     * @param location Filtro por localidade (opcional)
     * @param page     Número da página (default = 0)
     * @param limit    Tamanho da página (default = 12)
     * @return Página de vagas filtradas com metadados de paginação
     */
    public PageResponseDto<JobResponseDto> findAllJobs(
            String title,
            String location,
            WorkMode workMode,
            int page,
            int limit) {
        Specification<Job> filters = getFilters(title, location, workMode);
        Pageable pageRequest = PageRequest.of(page, limit);

        Page<Job> jobPage = repository.findAll(filters, pageRequest);
        List<JobResponseDto> content = jobPage.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());

        return new PageResponseDto<>(
                jobPage.getNumber(),
                jobPage.getSize(),
                jobPage.getTotalElements(),
                jobPage.getTotalPages(),
                jobPage.isLast(),
                content
        );
    }

    /**
     * Retorna uma vaga específica pelo ID.
     *
     * @param id UUID da vaga
     */
    public JobResponseDto findJobById(UUID id) {
        Job job = findJobOrThrow(id);
        return mapper.toResponseDto(job);
    }

    /**
     * Retorna todas as vagas criadas pela empresa do usuário logado.
     */
    public List<JobResponseDto> findJobsByCompany(UUID id) {
        Company company = companyService.findOptionalCompany(id);
        List<Job> jobs = repository.findAllByCompany(company);

        return jobs.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova vaga associada à empresa do usuário logado.
     *
     * @param dto Dados da vaga
     */
    @Transactional
    public JobResponseDto createJob(JobDto dto) {
        AppUser user = securityService.getLoggedUserOrThrow();

        Company company = companyService.findCompanyByUser(user);
        Job job = mapper.toEntity(dto);
        job.setCompany(company);

        Job saved = repository.save(job);
        return mapper.toResponseDto(saved);
    }

    /**
     * Atualiza os dados de uma vaga existente.
     *
     * @param jobId UUID da vaga
     * @param dto   Novos dados da vaga
     */
    @Transactional
    public JobResponseDto updateJob(UUID jobId, JobDto dto) {
        Job existingJob = findJobOrThrow(jobId);
        checkJobIsFromCompany(existingJob);

        if (existingJob.getExpiresAt().isBefore(LocalDate.now())) {
            if (!existingJob.getExpiresAt().equals(dto.expiresAt()))
                throw new NotAllowedException("Não é possível alterar uma vaga que já está encerrada.");
        }

        mapper.updateFromDto(dto, existingJob);

        Job updated = repository.save(existingJob);
        return mapper.toResponseDto(updated);
    }

    /**
     * Remove uma vaga com base no ID.
     *
     * @param id UUID da vaga
     */
    @Transactional
    public void deleteJob(UUID id) {
        Job job = findJobOrThrow(id);
        checkJobIsFromCompany(job);
        repository.delete(job);
    }

    /**
     * Retorna um relatório de vagas por empresa.
     *
     * @param id UUID da empresa
     */
    public List<JobReportDto> getReportByCompany(UUID id) {
        Company company = companyService.findOptionalCompany(id);
        return repository.findJobReportsByCompany(company);
    }

    /**
     * Retorna um relatório de vagas agrupadas por nome do grupo.
     *
     * @param groupName Nome do grupo
     */
    public List<JobReportDto> getReportByGroup(String groupName) {
        return repository.findJobReportsByGroup(groupName);
    }

    /**
     * Verifica se a vaga pertence ao usuário logado.
     *
     * @param job Vaga que seja verificada
     * @throws NotAllowedException se a empresa não for dona da vaga
     */
    public void checkJobIsFromCompany(Job job) {
        AppUser user = securityService.getLoggedUserOrThrow();
        Company company = companyService.findCompanyByUser(user);
        if (company.getId() != job.getCompany().getId())
            throw new NotAllowedException("Ação não permitida: A vaga não lhe pertence.");
    }

    /**
     * Busca uma vaga pelo ID ou lança exceção se não encontrada.
     *
     * @throws NotFoundException se não encontrar a vaga
     */
    public Job findJobOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vaga não encontrada."));
    }

    /**
     * Retorna os filtros que serão aplicados na paginação
     * @param title titulo da vaga
     * @param location local da vaga
     * @param workMode Modelo de trabalho da vaga
     * @return Spec com query com os filtros
     */
    private Specification<Job> getFilters(String title, String location, WorkMode workMode) {
        Specification<Job> specs = (root, query, cb) -> cb.conjunction();

        if (title != null && !title.isBlank())
            specs = specs.and(JobSpecs.titleLike(title));
        if (location != null && !location.isBlank())
            specs = specs.and(JobSpecs.locationLike(location));
        if (workMode != null)
            specs = specs.and(JobSpecs.workModeEqual(workMode));

        return specs;
    }
}

