package com.syncteam.hiresync.services;

import com.syncteam.hiresync.dtos.auth.AuthResponseDto;
import com.syncteam.hiresync.dtos.auth.LoginUserDto;
import com.syncteam.hiresync.dtos.candidates.CandidateDto;
import com.syncteam.hiresync.dtos.candidates.CandidateResponseDto;
import com.syncteam.hiresync.dtos.companies.CompanyDto;
import com.syncteam.hiresync.dtos.companies.CompanyResponseDto;
import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Candidate;
import com.syncteam.hiresync.entities.Company;
import com.syncteam.hiresync.entities.enums.Role;
import com.syncteam.hiresync.exceptions.ConflictException;
import com.syncteam.hiresync.exceptions.UserNotFoundException;
import com.syncteam.hiresync.mappers.CandidateMapper;
import com.syncteam.hiresync.mappers.CompanyMapper;
import com.syncteam.hiresync.repositories.AppUserRepository;
import com.syncteam.hiresync.repositories.CandidateRepository;
import com.syncteam.hiresync.repositories.CompanyRepository;
import com.syncteam.hiresync.security.JwtService;
import com.syncteam.hiresync.security.SecurityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;

    private final CandidateMapper candidateMapper;
    private final CompanyMapper companyMapper;

    private final JwtService jwtService;
    private final SecurityService securityService;

    /**
     * Cria e registra um novo candidato.
     * @param candidateDto dados do candidato
     * @param response resposta HTTP usada para adicionar o cookie JWT
     * @return objeto DTO com os dados do candidato criado
     */
    @Transactional
    public CandidateResponseDto createCandidate(CandidateDto candidateDto, HttpServletResponse response) {
        checkUserExists(candidateDto.email());
        checkCpfExists(candidateDto.cpf());
        Candidate candidateEntity = candidateMapper.toEntity(candidateDto);
        candidateEntity.getUser().setRoles(Set.of(Role.CANDIDATE));

        String rawPassword = candidateEntity.getUser().getPassword();
        candidateEntity.getUser().setPassword(jwtService.encodePassword(rawPassword));

        Candidate candidate = candidateRepository.save(candidateEntity);
        String token = jwtService.generateToken(candidate.getUser());
        jwtService.setJwtCookie(response, token);

        return candidateMapper.toDTO(candidate);
    }

    /**
     * Cria e registra uma nova empresa.
     * @param companyDto dados da empresa
     * @param response resposta HTTP usada para adicionar o cookie JWT
     * @return objeto DTO com os dados da empresa criada
     */
    @Transactional
    public CompanyResponseDto createCompany(CompanyDto companyDto, HttpServletResponse response) {
        checkUserExists(companyDto.email());
        checkCnpjExists(companyDto.cnpj());
        Company companyEntity = companyMapper.toEntity(companyDto);
        companyEntity.getUser().setRoles(Set.of(Role.COMPANY));

        String rawPassword = companyEntity.getUser().getPassword();
        companyEntity.getUser().setPassword(jwtService.encodePassword(rawPassword));

        Company company = companyRepository.save(companyEntity);
        String token = jwtService.generateToken(company.getUser());
        jwtService.setJwtCookie(response, token);

        return companyMapper.toDTO(company);
    }

    /**
     * Realiza login do usuário e retorna token e dados do perfil.
     * @param userDto DTO com email e senha
     * @param response resposta HTTP para adicionar o cookie JWT
     * @return DTO com token de autenticação e informações do usuário
     */
    public AuthResponseDto login(LoginUserDto userDto, HttpServletResponse response) {
        AppUser user = userRepository.findByEmail(userDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        jwtService.isMatching(userDto.password(), user.getPassword());
        String token = jwtService.generateToken(user);

        Object userDetails;
        if (user.getRoles().contains(Role.CANDIDATE)) {
            Candidate candidate = findCandidateByUser(user);
            userDetails = candidateMapper.toDTO(candidate);
        } else if (user.getRoles().contains(Role.COMPANY)) {
            Company company = findCompanyByUser(user);
            userDetails = companyMapper.toDTO(company);
        } else
            throw new BadCredentialsException("Unknown role");

        jwtService.setJwtCookie(response, token);
        return new AuthResponseDto(userDetails, token);
    }

    /**
     * Remove o token do cookie
     * @param response objeto com o cookie JWT
     */
    public void logout(HttpServletResponse response) {
        jwtService.expireJwtCookie(response);
    }

    /**
     * Verifica qual é o usuário logado e retorna o objeto
     * Faz Throw caso não exista usuário com Token Válido
     */
    public Object checkUser() {
        AppUser user = securityService.getLoggedUserOrThrow();
        if (user.getRoles().contains(Role.CANDIDATE))
            return candidateMapper.toDTO(findCandidateByUser(user));

        if (user.getRoles().contains(Role.COMPANY))
            return companyMapper.toDTO(findCompanyByUser(user));

        return null;
    }

    /**
     * Busca um usuário por email.
     * @param email endereço de email
     * @return Optional com o usuário, se existir
     */
    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Verifica se já existe um usuário com o email informado.
     * @param email email do usuário
     */
    public void checkUserExists(String email) {
        if (userRepository.existsByEmail(email)) throwConflict();
    }

    /**
     * Verifica se já existe um candidato com o CPF informado.
     * @param cpf CPF do candidato
     */
    public void checkCpfExists(String cpf) {
        if (candidateRepository.existsByCpf(cpf)) throwConflict();
    }

    /**
     * Verifica se já existe uma empresa com o CNPJ informado.
     * @param cnpj CNPJ da empresa
     */
    public void checkCnpjExists(String cnpj) {
        if (companyRepository.existsByCnpj(cnpj)) throwConflict();
    }

    /**
     * Lança exceção de conflito (usuário já existente).
     */
    private void throwConflict() {
        throw new ConflictException("Já existe um usuário com esses dados");
    }

    /**
     * Busca um candidato com base no usuário (AppUser).
     * @param user usuário associado ao candidato
     * @return entidade Candidate correspondente
     * @throws UserNotFoundException se nenhum candidato for associado ao usuário
     */
    public Candidate findCandidateByUser(AppUser user) {
        return candidateRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Candidato não encontrado"));
    }

    /**
     * Busca uma empresa associada a um usuário específico.
     * @param user objeto AppUser associado
     * @return entidade Company correspondente
     * @throws UserNotFoundException se nenhuma empresa for encontrada
     */
    public Company findCompanyByUser(AppUser user) {
        return companyRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Empresa não encontrada"));
    }
}