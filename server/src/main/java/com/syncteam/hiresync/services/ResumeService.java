package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.resumes.ResumeDto;
import com.syncteam.hiresync.dtos.resumes.ResumeResponseDto;
import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Resume;
import com.syncteam.hiresync.exceptions.ConflictException;
import com.syncteam.hiresync.exceptions.NotFoundException;
import com.syncteam.hiresync.mappers.ResumeMapper;
import com.syncteam.hiresync.repositories.ResumeRepository;
import com.syncteam.hiresync.validators.ResumeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    @Value("${app.resume.upload-dir}")
    private String uploadDir;

    private final ResumeRepository repository;
    private final CandidateService candidateService;
    private final ResumeMapper mapper;
    private final ResumeValidator validator;

    /**
     * Busca todos os currículos no banco de dados.
     * @return Lista de objetos ResumeResponseDto com os dados dos currículos.
     */
    public List<ResumeResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca um currículo específico por ID.
     * @param id UUID do currículo.
     * @return DTO com os dados do currículo.
     */
    public ResumeResponseDto findById(UUID id) {
        Resume resume = findOrThrow(id);
        validator.validateOwnership(resume);
        return mapper.toResponseDto(resume);
    }

    /**
     * Busca currículo de um candidato específico.
     * @param candidateId UUID do candidato.
     * @return DTO com os dados do currículo do candidato.
     */
    public ResumeResponseDto findByCandidate(UUID candidateId) {
        Candidate candidate = candidateService.findOptionalCandidate(candidateId);
        Resume resume = repository.findByCandidate(candidate)
                .orElseThrow(() -> new NotFoundException("Currículo do candidato não encontrado"));
        return mapper.toResponseDto(resume);
    }

    /**
     * Cria um currículo novo para o candidato logado.
     * @param dto Dados do currículo.
     * @return DTO do currículo criado.
     * @throws ConflictException se o candidato já possuir um currículo cadastrado.
     */
    @Transactional
    public ResumeResponseDto createResume(ResumeDto dto) {
        Candidate candidate = validator.validateLoggedCandidate();
        if (repository.existsByCandidate(candidate))
            throw new ConflictException("Já existe um currículo vinculado ao candidato");

        Resume resume = mapper.toEntity(dto);
        resume.setCandidate(candidate);

        return mapper.toResponseDto(repository.save(resume));
    }

    /**
     * Atualiza os dados de um currículo existente.
     * @param id UUID do currículo.
     * @param dto Novos dados a serem aplicados.
     * @return DTO atualizado.
     */
    @Transactional
    public ResumeResponseDto updateResume(UUID id, ResumeDto dto) {
        Resume resume = findOrThrow(id);
        validator.validateOwnership(resume);

        mapper.updateFromDto(dto, resume);
        return mapper.toResponseDto(repository.save(resume));
    }

    /**
     * Remove um currículo existente do banco de dados.
     * @param id UUID do currículo.
     */
    @Transactional
    public void deleteResume(UUID id) {
        Resume resume = findOrThrow(id);
        validator.validateOwnership(resume);

        Candidate candidate = resume.getCandidate();
        candidate.setResume(null);
    }

    /**
     * Faz upload de um novo arquivo de currículo (PDF) para o candidato logado.
     * Caso já exista um arquivo, ele será substituído.
     * @param file Arquivo Multipart enviado pelo frontend.
     * @return DTO atualizado com a URL do novo arquivo.
     */
    @Transactional
    public ResumeResponseDto uploadResume(MultipartFile file) {
        Candidate candidate = validator.validateLoggedCandidate();
        validator.validateFile(file);

        Resume resume = Optional.ofNullable(candidate.getResume())
                .orElseGet(() -> {
                    Resume newResume = new Resume();
                    newResume.setCandidate(candidate);
                    return newResume;
                });

        deleteOldResumeFileIfExists(resume);

        String fileName = generateFileName(candidate.getId(), file.getOriginalFilename());
        Path filePath = Path.of(uploadDir, fileName);
        saveFile(file, filePath);

        resume.setFileUrl("/files/resumes/" + fileName);
        Resume saved = repository.save(resume);

        return mapper.toResponseDto(saved);
    }

    /**
     * Deleta o arquivo antigo do currículo, se existir.
     * @param resume Entidade do currículo.
     */
    private void deleteOldResumeFileIfExists(Resume resume) {
        String oldFileUrl = resume.getFileUrl();
        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
            Path oldFilePath = Path.of(uploadDir, oldFileUrl.replace("/files/resumes/", ""));
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                System.out.println("Failed to delete old resume file:" + oldFilePath);
            }
        }
    }

    /**
     * Salva o arquivo recebido em disco.
     * @param file Multipart file recebido do cliente.
     * @param filePath Caminho completo onde o arquivo será salvo.
     */
    private void saveFile(MultipartFile file, Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    /**
     * Gera um nome único para o arquivo do currículo.
     * @param candidateId ID do candidato.
     * @param originalName Nome original do arquivo.
     * @return Nome final no formato UUID_resume_timestamp.pdf
     */
    private String generateFileName(UUID candidateId, String originalName) {
        return candidateId + "_resume_" + System.currentTimeMillis() + ".pdf";
    }

    /**
     * Busca um currículo por ID ou lança exceção se não existir.
     * @param id UUID do currículo.
     * @return Entidade Resume.
     * @throws NotFoundException se não encontrado.
     */
    private Resume findOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Currículo não encontrado."));
    }
}

