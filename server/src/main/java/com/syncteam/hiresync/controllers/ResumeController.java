package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.resumes.ResumeDto;
import com.syncteam.hiresync.dtos.resumes.ResumeResponseDto;
import com.syncteam.hiresync.services.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    /**
     * Retorna todos os currículos do sistema.
     * Somente acessível por administradores.
     * @return Lista de currículos.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResumeResponseDto>> getAllResumes() {
        List<ResumeResponseDto> resumes = resumeService.findAll();
        return ResponseEntity.ok(resumes);
    }

    /**
     * Retorna um currículo específico pelo ID.
     * @param id UUID do currículo.
     * @return Dados do currículo.
     */
    @GetMapping("{id}")
    public ResponseEntity<ResumeResponseDto> getResumeById(@PathVariable UUID id) {
        ResumeResponseDto resume = resumeService.findById(id);
        return ResponseEntity.ok(resume);
    }

    /**
     * Retorna o currículo de um candidato pelo ID do candidato.
     * @param candidateId UUID do candidato.
     * @return Dados do currículo.
     */
    @GetMapping("/candidate/{id}")
    public ResponseEntity<ResumeResponseDto> getResumeByCandidate(@PathVariable("id") UUID candidateId) {
        ResumeResponseDto resume = resumeService.findByCandidate(candidateId);
        return ResponseEntity.ok(resume);
    }

    /**
     * Cria um novo currículo.
     * Apenas usuários com papel de candidato podem acessar.
     * @param dto Dados do currículo.
     * @return Currículo criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ResumeResponseDto> createResume(@RequestBody @Valid ResumeDto dto) {
        ResumeResponseDto createdResume = resumeService.createResume(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
    }

    /**
     * Atualiza um currículo existente.
     * Apenas usuários com papel de candidato podem acessar.
     * @param id UUID do currículo.
     * @param dto Novos dados do currículo.
     * @return Currículo atualizado.
     */
    @PutMapping("{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ResumeResponseDto> updateResume(@PathVariable UUID id, @RequestBody @Valid ResumeDto dto) {
        ResumeResponseDto updatedResume = resumeService.updateResume(id, dto);
        return ResponseEntity.ok(updatedResume);
    }

    /**
     * Exclui um currículo.
     * Pode ser acessado por candidatos e administradores.
     * @param id UUID do currículo.
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('CANDIDATE', 'ADMIN')")
    public ResponseEntity<Void> deleteResume(@PathVariable UUID id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Faz upload de um arquivo PDF de currículo.
     * Apenas usuários com papel de candidato podem acessar.
     * @param file Arquivo enviado.
     * @return Currículo com nova URL do arquivo.
     */
    @PostMapping("/upload")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ResumeResponseDto> uploadResume(@RequestParam("file") MultipartFile file) {
        ResumeResponseDto uploadedResume = resumeService.uploadResume(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedResume);
    }
}
