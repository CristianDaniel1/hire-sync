package com.syncteam.hiresync.validators;

import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Resume;
import com.syncteam.hiresync.entities.enums.Role;
import com.syncteam.hiresync.exceptions.InvalidFieldException;
import com.syncteam.hiresync.security.SecurityService;
import com.syncteam.hiresync.services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ResumeValidator {

    private final SecurityService securityService;
    private final CandidateService candidateService;

    public void validateFile(MultipartFile file) {
        if (file.isEmpty())
            throw new InvalidFieldException("resume", "File is empty.");

        if (!Objects.equals(file.getContentType(), "application/pdf"))
            throw new InvalidFieldException("resume", "Only PDF files are allowed.");

        if (file.getSize() > 5 * 1024 * 1024)
            throw new InvalidFieldException("resume", "File must be less than 5MB.");
    }

    public Candidate validateLoggedCandidate() {
        AppUser user = securityService.getLoggedUserOrThrow();
        if (!user.getRoles().contains(Role.CANDIDATE)) {
            throw new AccessDeniedException("Only candidates can access resumes");
        }
        return candidateService.findCandidateByUser(user);
    }

    public void validateOwnership(Resume resume) {
        AppUser loggedUser = securityService.getLoggedUserOrThrow();
        boolean isOwner = resume.getCandidate().getUser().getId().equals(loggedUser.getId());
        boolean isAdmin = loggedUser.getRoles().contains(Role.ADMIN);
        if (!isOwner && !isAdmin)
            throw new AccessDeniedException("You do not have permission to access this resume");
    }
}
