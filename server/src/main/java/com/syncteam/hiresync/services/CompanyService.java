package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.companies.CompanyResponseDto;
import com.syncteam.hiresync.dtos.companies.UpdateCompanyDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.exceptions.UserNotFoundException;
import com.syncteam.hiresync.mappers.CompanyMapper;
import com.syncteam.hiresync.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    /**
     * Retorna todas as empresas cadastradas no sistema.
     * @return lista de objetos DTO representando empresas
     */
    public List<CompanyResponseDto> findAllCompanies() {
        List<Company> companies = repository.findAll();

        return companies
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
 
    /**
     * Busca uma empresa pelo seu identificador UUID.
     * @param id identificador único da empresa
     * @return DTO com os dados da empresa
     * @throws UserNotFoundException se a empresa não for encontrada
     */
    public CompanyResponseDto findCompanyById(UUID id) {
        Company company = findOptionalCompany(id);
        return mapper.toDTO(company);
    }

    /**
     * Busca empresas que pertençam a um grupo específico.
     * @param groupName nome do grupo (ex: "TI", "Educação")
     * @return lista de empresas no grupo especificado
     */
    public List<CompanyResponseDto> findCompaniesByGroup(String groupName) {
        List<Company> companies = repository.findByGroupName(groupName);

        return companies
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente os dados de uma empresa existente.
     * @param id identificador único da empresa
     * @param dto objeto com os campos a serem atualizados
     * @return DTO com os dados atualizados da empresa
     */
    @Transactional
    public CompanyResponseDto updateCompany(UUID id, UpdateCompanyDto dto) {
        Company companyEntity = findOptionalCompany(id);
        Company company = mapper.partialUpdateCompanyFromDto(dto, companyEntity);

        Company updatedCompany = repository.save(company);
        return mapper.toDTO(updatedCompany);
    }

    /**
     * Remove uma empresa do sistema com base no ID.
     * @param id identificador único da empresa
     * @return DTO com os dados da empresa que foi deletada
     */
    @Transactional
    public CompanyResponseDto deleteCompany(UUID id) {
        Company company = findOptionalCompany(id);
        repository.delete(company);
        return mapper.toDTO(company);
    }

    /**
     * Busca uma empresa associada a um usuário específico.
     * @param user objeto AppUser associado
     * @return entidade Company correspondente
     * @throws UserNotFoundException se nenhuma empresa for encontrada
     */
    public Company findCompanyByUser(AppUser user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Empresa não encontrada"));
    }

    /**
     * Busca uma empresa por ID e lança exceção se não encontrada.
     * @param id identificador da empresa
     * @return entidade Company correspondente
     * @throws UserNotFoundException se a empresa não existir
     */
    public Company findOptionalCompany(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Empresa não encontrada"));
    }
}