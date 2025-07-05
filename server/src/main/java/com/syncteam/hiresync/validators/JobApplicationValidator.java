package com.syncteam.hiresync.validators;

import com.syncteam.hiresync.entities.*;
import com.syncteam.hiresync.entities.enums.Contract;
import com.syncteam.hiresync.exceptions.ConflictException;
import com.syncteam.hiresync.exceptions.NotAllowedException;
import com.syncteam.hiresync.repositories.JobApplicationRepository;
import com.syncteam.hiresync.security.SecurityService;
import com.syncteam.hiresync.services.CandidateService;
import com.syncteam.hiresync.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobApplicationValidator {

    private final JobApplicationRepository repository;
    private final SecurityService securityService;
    private final CandidateService candidateService;
    private final CompanyService companyService;

    public void validateRequirements(Job job, Candidate candidate) {
        validateCanApply(job);
        if (repository.existsByJobAndCandidate(job, candidate))
            throw new ConflictException("Você já se candidatou nesta vaga.");

        Contract contractType = job.getContractType();

        if (contractType == Contract.INTERN)
            validateInternRequirements(job, candidate);
        if (contractType == Contract.TRAINEE)
            validateTraineeRequirements(job, candidate);
    }

    private void validateInternRequirements(Job job, Candidate candidate) {
        currentlyStudying(candidate.isCurrentlyStudying());
        if (job.getCourseRequired() != null &&
                !job.getCourseRequired().equalsIgnoreCase(candidate.getCourse()))
            throw new NotAllowedException("O seu curso não faz parte dos cursos requeridos na vaga.");

        if (job.getMinSemester() != null &&
                candidate.getCurrentSemester() < job.getMinSemester())
            throw new NotAllowedException("Você não possui o semestre mínimo da vaga.");
    }

    private void validateTraineeRequirements(Job job, Candidate candidate) {
        if (job.getGraduationYearRequired() != null &&
                candidate.getGraduationYear() != null &&
                candidate.getGraduationYear().isAfter(job.getGraduationYearRequired()))
            throw new NotAllowedException("Seu ano de graduação não é compatível com a vaga.");
    }

    private void currentlyStudying(boolean isStudying) {
        if (!isStudying)
            throw new NotAllowedException("Você precisa estar matriculado em algum curso para se candidatar.");
    }

    public void validateLoggedCompany(JobApplication application) {
        AppUser loggedUser = securityService.getLoggedUserOrThrow();
        Company company = application.getJob().getCompany();

        boolean isCompanyJob = company.getUser().getId().equals(loggedUser.getId());

        if (!isCompanyJob)
            throw new AccessDeniedException("Access denied");
    }

    public void validateCanApply(Job job) {
        if (!job.getExpiresAt().isAfter(LocalDate.now()))
            throw new NotAllowedException("Não é possível candidatar a uma vaga que já está encerrada.");
    }

    public void validateRelatedUsers(JobApplication application) {
        UUID loggedUserId = securityService.getLoggedUserOrThrow().getId();
        UUID candidateUserId = application.getCandidate().getUser().getId();
        UUID companyUserId = application.getJob().getCompany().getUser().getId();

        boolean isOwner = candidateUserId.equals(loggedUserId);
        boolean isCompany = companyUserId.equals(loggedUserId);

        if (!isOwner && !isCompany)
            throw new AccessDeniedException("You do not have permission to access this job application");
    }

    public Candidate validateCandidate(UUID candidateId) {
        return candidateService.findOptionalCandidate(candidateId);
    }

    public Company validateCompany(UUID companyId) {
        return companyService.findOptionalCompany(companyId);
    }

    public Candidate validateLoggedCandidate() {
        AppUser user = securityService.getLoggedUserOrThrow();
        return candidateService.findCandidateByUser(user);
    }
}
