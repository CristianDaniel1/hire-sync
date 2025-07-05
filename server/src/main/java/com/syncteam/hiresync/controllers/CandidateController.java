package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.candidates.CandidateResponseDto;
import com.syncteam.hiresync.dtos.candidates.UpdateCandidateDto;
import com.syncteam.hiresync.services.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService service;

    /**
     * Endpoint para obter todos os candidatos cadastrados.
     * Somente acessível por administradores.
     * @return lista de candidatos no formato DTO
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CandidateResponseDto>> getCandidates() {
        List<CandidateResponseDto> candidates = service.findAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    /**
     * Endpoint para obter um candidato específico pelo ID.
     * @param id UUID do candidato
     * @return DTO com os dados do candidato encontrado
     */    
    @GetMapping("{id}")
    public ResponseEntity<CandidateResponseDto> getCandidateById(@PathVariable("id") UUID id) {
        CandidateResponseDto candidate = service.findCandidateById(id);
        return ResponseEntity.ok(candidate);
    }

    /**
     * Endpoint para atualizar os dados de um candidato.
     * Requer papel CANDIDATE ou ADMIN.
     * @param id UUID do candidato
     * @param dto DTO contendo os campos a serem atualizados
     * @return DTO com os dados atualizados do candidato
     */
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('CANDIDATE', 'ADMIN')")
    public ResponseEntity<CandidateResponseDto> updateCandidate(
            @PathVariable("id") UUID id, @RequestBody @Valid UpdateCandidateDto dto) {
        CandidateResponseDto updatedCandidate = service.updateCandidate(id, dto);
        return ResponseEntity.ok(updatedCandidate);
    }

    /**
     * Endpoint para deletar um candidato com base no ID.
     * Requer papel CANDIDATE ou ADMIN.
     * @param id UUID do candidato a ser excluído
     * @return DTO com os dados do candidato que foi removido
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('CANDIDATE', 'ADMIN')")
    public ResponseEntity<CandidateResponseDto> deleteCandidate(@PathVariable("id") UUID id) {
        CandidateResponseDto deletedCandidate = service.deleteCandidate(id);
        return ResponseEntity.ok(deletedCandidate);
    }
}
