package com.syncteam.hiresync.services;

import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.entities.Job;
import com.syncteam.hiresync.entities.JobApplication;
import com.syncteam.hiresync.entities.enums.Status;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${app.template-path}")
    private String templatePath;
    @Value("${app.template-path-update}")
    private String templatePathUpdate;

    public void sendApplicationEmail(Candidate candidate, Job job) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Company company = job.getCompany();

            helper.setFrom(from);
            helper.setTo(company.getUser().getEmail());
            helper.setSubject("Nova candidatura recebida - " + job.getTitle());

            String htmlContent = buildCandidateApplicationEmail(candidate, job, company);
            helper.setText(htmlContent, true);

            if (candidate.getResume() != null && candidate.getResume().getFileUrl() != null) {
                File resumeFile = new File("uploads/resumes" + File.separator + getFileName(candidate.getResume().getFileUrl()));
                if (resumeFile.exists()) {
                    helper.addAttachment("curriculo.pdf", new FileSystemResource(resumeFile));
                }
            }

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email de candidatura");
        }
    }

    public void sendStatusUpdateEmail(JobApplication application) {
        Candidate candidate = application.getCandidate();

        String email = candidate.getUser().getEmail();
        String candidateName = candidate.getUser().getName();
        String jobTitle = application.getJob().getTitle();
        Status status = application.getStatus();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Status da sua candidatura foi atualizado");

            String template = Files.readString(Path.of(templatePathUpdate));
            String htmlContent = template
                    .replace("{{CANDIDATE_NAME}}", candidateName)
                    .replace("{{COMPANY_NAME}}", application.getJob().getCompany().getUser().getName())
                    .replace("{{APPLICATION_DATE}}", application.getCreatedAt().toLocalDate().toString())
                    .replace("{{UPDATE_DATE}}", application.getModifiedAt().toLocalDate().toString())
                    .replace("{{JOB_TITLE}}", jobTitle)
                    .replace("{{APPLICATION_STATUS}}", setStatus(status));

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail de atualização de status", e);
        }
    }

    private String setStatus(Status status) {
        if (status == Status.INTERVIEW)
            return "Fase de Entrevistas";
        if (status == Status.APPROVED)
            return "APROVADO";
        if (status == Status.REJECTED)
            return "Não aprovado";
        return "Em Análise";
    }

    private String buildCandidateApplicationEmail(Candidate c, Job j, Company company) {
        String template = getEmailTemplateContent(templatePath);
        return template
                .replace("{{candidateName}}", c.getUser().getName())
                .replace("{{candidateEmail}}", c.getUser().getEmail())
                .replace("{{candidatePhone}}", c.getPhone())
                .replace("{{candidateAddress}}", c.getAddress())
                .replace("{{jobTitle}}", j.getTitle())
                .replace("{{jobLocation}}", j.getLocation())
                .replace("{{companyName}}", company.getUser().getName())
                .replace("{{jobDeadline}}", j.getExpiresAt().toString());
    }

    private String getEmailTemplateContent(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o template de email", e);
        }
    }

    private String getFileName(String fileUrl) {
        return Path.of(fileUrl).getFileName().toString();
    }
}


