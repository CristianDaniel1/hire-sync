package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.candidates.CandidateResponseDto;
import com.syncteam.hiresync.dtos.candidates.UpdateCandidateDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.exceptions.UserNotFoundException;
import com.syncteam.hiresync.mappers.CandidateMapper;
import com.syncteam.hiresync.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository repository;
    private final CandidateMapper mapper;

    /**
     * Retorna todos os candidatos cadastrados no sistema.
     * @return lista de objetos DTO representando os candidatos
     */
    public List<CandidateResponseDto> findAllCandidates() {
        List<Candidate> candidatesResult = repository.findAll();

        return candidatesResult
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca um candidato pelo seu identificador UUID.
     * @param id identificador do candidato
     * @return DTO com os dados do candidato encontrado
     * @throws UserNotFoundException se o candidato não for encontrado
     */
    public CandidateResponseDto findCandidateById(UUID id) {
        Candidate candidate = findOptionalCandidate(id);
        return mapper.toDTO(candidate);
    }

    /**
     * Atualiza parcialmente os dados de um candidato.
     * @param id identificador do candidato a ser atualizado
     * @param dto objeto com os campos a serem atualizados
     * @return DTO com os dados atualizados do candidato
     */
    @Transactional
    public CandidateResponseDto updateCandidate(UUID id, UpdateCandidateDto dto) {
        Candidate candidateEntity = findOptionalCandidate(id);
        Candidate candidate = mapper.partialUpdateCandidateFromDto(dto, candidateEntity);

        Candidate updatedCandidate = repository.save(candidate);
        return mapper.toDTO(updatedCandidate);
    }

    /**
     * Remove um candidato do sistema com base no UUID.
     * @param id identificador do candidato a ser excluído
     * @return DTO com os dados do candidato excluído
     */
    @Transactional
    public CandidateResponseDto deleteCandidate(UUID id) {
        Candidate candidate = findOptionalCandidate(id);
        repository.delete(candidate);
        return mapper.toDTO(candidate);
    }

    /**
     * Busca um candidato com base no usuário (AppUser).
     * @param user usuário associado ao candidato
     * @return entidade Candidate correspondente
     * @throws UserNotFoundException se nenhum candidato for associado ao usuário
     */
    public Candidate findCandidateByUser(AppUser user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Candidato não encontrado"));
    }

    /**
     * Busca um candidato por ID e lança exceção se não existir.
     * @param id identificador do candidato
     * @return entidade Candidate encontrada
     * @throws UserNotFoundException se não existir um candidato com o ID fornecido
     */
    public Candidate findOptionalCandidate(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Candidato não encontrado"));
    }
}
