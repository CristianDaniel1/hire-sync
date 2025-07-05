package com.syncteam.hiresync.controllers;

import com.syncteam.hiresync.dtos.auth.AuthResponseDto;
import com.syncteam.hiresync.dtos.auth.LoginUserDto;
import com.syncteam.hiresync.dtos.candidates.CandidateDto;
import com.syncteam.hiresync.dtos.candidates.CandidateResponseDto;
import com.syncteam.hiresync.dtos.companies.CompanyDto;
import com.syncteam.hiresync.dtos.companies.CompanyResponseDto;
import com.syncteam.hiresync.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para login de usuários (candidatos ou empresas).
     * @param dto objeto contendo email e senha do usuário
     * @param response objeto usado para definir o cookie com o token JWT
     * @return resposta com dados do usuário autenticado e token de acesso
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginUserDto dto,
            HttpServletResponse response) {
        AuthResponseDto authUser = authService.login(dto, response);
        return ResponseEntity.ok(authUser);
    }

    /**
     * Endpoint para registrar um novo candidato.
     * @param dto objeto com os dados do candidato
     * @param response objeto para definir o cookie com o token JWT
     * @return resposta com os dados do candidato criado
     */
    @PostMapping("/register/candidate")
    public ResponseEntity<CandidateResponseDto> registerCandidate(
            @Valid @RequestBody CandidateDto dto,
            HttpServletResponse response) {
        CandidateResponseDto candidate = authService.createCandidate(dto, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
    }

    /**
     * Endpoint para registrar uma nova empresa.
     * @param dto objeto com os dados da empresa
     * @param response objeto para definir o cookie com o token JWT
     * @return resposta com os dados da empresa criada
     */
    @PostMapping("/register/company")
    public ResponseEntity<CompanyResponseDto> registerCompany(
            @Valid @RequestBody CompanyDto dto,
            HttpServletResponse response) {
        CompanyResponseDto company = authService.createCompany(dto, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    /**
     * Endpoint para logout do usuário.
     * @param response objeto que remove o cookie JWT
     * @return mensagem de logout realizado com sucesso
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Successfully logged out.");
    }

    /**
     * Endpoint para verificar a autenticação do usuário.
     * @return objeto genérico do usuário
     */
    @GetMapping("/check")
    public ResponseEntity<Object> check() {
        Object user = authService.checkUser();
        return ResponseEntity.ok(user);
    }
}

