package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.jobApplications.JobApplicationDto;
import com.syncteam.hiresync.dtos.jobApplications.JobApplicationResponseDto;
import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.entities.Job;
import com.syncteam.hiresync.entities.JobApplication;
import com.syncteam.hiresync.entities.enums.Status;
import com.syncteam.hiresync.exceptions.NotFoundException;
import com.syncteam.hiresync.mappers.JobApplicationMapper;
import com.syncteam.hiresync.repositories.JobApplicationRepository;
import com.syncteam.hiresync.repositories.JobRepository;
import com.syncteam.hiresync.validators.JobApplicationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    private final JobApplicationValidator validator;
    private final JobApplicationMapper mapper;

    /**
     * Retorna todas as candidaturas cadastradas.
     */
    public List<JobApplicationResponseDto> findAllApplications() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma candidatura por ID, validando se o usuário tem permissão.
     * @param id UUID da candidatura
     */
    public JobApplicationResponseDto findApplicationById(UUID id) {
        JobApplication application = findApplicationOrThrow(id);
        validator.validateRelatedUsers(application);
        return mapper.toResponseDto(application);
    }

    /**
     * Retorna todas as candidaturas feitas por um candidato.
     * @param candidateId UUID do candidato
     */
    public List<JobApplicationResponseDto> findApplicationsByCandidate(UUID candidateId) {
        Candidate candidate = validator.validateCandidate(candidateId);
        return repository.findAllByCandidate(candidate).stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todas as candidaturas feitas para vagas de uma empresa.
     * @param companyId UUID da empresa
     */
    public List<JobApplicationResponseDto> findApplicationsByCompany(UUID companyId) {
        Company company = validator.validateCompany(companyId);
        List<Job> jobs = jobRepository.findAllByCompany(company);

        List<JobApplication> applications = repository.findAllByJobIn(jobs);

        return applications.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todas as candidaturas feitas para uma vaga específica.
     * @param jobId UUID da vaga
     */
    public List<JobApplicationResponseDto> findApplicationsByJob(UUID jobId) {
        Job job = findJobOrThrow(jobId);
        List<JobApplication> applications = repository.findAllByJob(job);

        return applications.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova candidatura para o candidato autenticado.
     * Valida se o candidato atende aos requisitos da vaga.
     * @param dto dados da candidatura
     */
    @Transactional
    public JobApplicationResponseDto createApplication(JobApplicationDto dto) {
        Job job = findJobOrThrow(dto.jobId());
        Candidate candidate = validator.validateLoggedCandidate();
        validator.validateRequirements(job, candidate);

        JobApplication application = mapper.toEntity(dto);
        application.setJob(job);
        application.setStatus(Status.PENDING);
        application.setCandidate(candidate);

        emailService.sendApplicationEmail(candidate, job);
        return mapper.toResponseDto(repository.save(application));
    }

    /**
     * Atualiza os dados de uma candidatura existente.
     * Valida se o usuário tem permissão sobre ela.
     * @param id UUID da candidatura
     * @param dto dados novos da candidatura
     */
    @Transactional
    public JobApplicationResponseDto updateApplication(UUID id, JobApplicationDto dto) {
        JobApplication application = findApplicationOrThrow(id);
        validator.validateLoggedCompany(application);

        mapper.updateFromDto(dto, application);
        JobApplication updatedApplication = repository.save(application);

        emailService.sendStatusUpdateEmail(updatedApplication);
        return mapper.toResponseDto(updatedApplication);
    }

    /**
     * Exclui uma candidatura, após validar se a candidatura existe.
     * @param id UUID da candidatura
     */
    @Transactional
    public void deleteApplication(UUID id) {
        JobApplication application = findApplicationOrThrow(id);
        repository.delete(application);
    }

    /**
     * Busca uma vaga pelo ID ou lança exceção se não encontrada.
     * @throws NotFoundException se não encontrar a vaga
     */
    private Job findJobOrThrow(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vaga não encontrada."));
    }

    /**
     * Busca uma candidatura pelo ID ou lança exceção se não encontrada.
     * @param id UUID da candidatura
     * @throws NotFoundException se não encontrar a candidatura
     */
    private JobApplication findApplicationOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidatura não encontrada."));
    }
}
