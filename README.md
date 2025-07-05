# 💼 HireSync - Temos Vagas

<img src="https://i.imgur.com/Ap53ZQA.png" alt="Página inicial do HireSync" width="800" />

**HireSync** é uma plataforma full-stack para facilitar o processo de recrutamento entre candidatos e empresas de tecnologia. O sistema permite que candidatos se inscrevam em vagas, criem e enviem currículos em PDF, e que empresas gerenciem suas oportunidades com eficiência.

> Projeto acadêmico full-stack desenvolvido com foco em boas práticas, segurança, arquitetura escalável e usabilidade moderna. Inspirado por portais de recrutamento como Gupy entre outros.

---

## ⚙️ Tecnologias Utilizadas

| Front-End                    | Back-End                           | DevOps / Outros |
| ---------------------------- | ---------------------------------- | --------------- |
| React.js                     | Spring Boot (Java 21)              | Docker          |
| TypeScript                   | Spring Security (JWT + Cookies)    | PostgreSQL      |
| TailwindCSS                  | Spring Data JPA                    |                 |
| React Router                 | Spring Mail (JavaMailSender)       |                 |
| TanStack Query (React Query) | Bean Validation (DTOs + Hibernate) |                 |
| Zustand                      | Upload e leitura de arquivos PDF   |                 |

---

## 🧩 Arquitetura

- **Backend** com autenticação segura via **JWT (em cookie HttpOnly)**.
- Utilização de **Spring Security** com múltiplas chains de autenticação.
- Organização baseada em **Camadas + DTOs + Service Layer**.
- Front-end moderno com **Hooks + Zod + Zustand + Query Layer**.
- Upload de currículo em PDF com validações (tipo e tamanho) e link público para visualização ou download.

---

## 🔐 Autenticação

O sistema usa **JWT** armazenado em **cookies HttpOnly**, com controle de acesso baseado em **roles**:

- `ADMIN`
- `CANDIDATE`
- `COMPANY`

---

## ✨ Funcionalidades Principais

### 👤 Candidatos

- Criar conta e login seguro.
- Preencher dados de perfil.
- Criar currículo completo (incluindo PDF).
- Candidatar-se a vagas disponíveis.
- Consultar status das candidaturas.

### 🏢 Empresas

- Criar conta de empresa.
- Criar, editar e remover vagas.
- Ver todas as candidaturas em suas vagas.
- Acompanhar relatórios por empresa e por grupo.
- Filtrar vagas por curso, localidade, semestre e mais.

---

## 📄 Exemplo de Endpoint

### 🔍 GET `/jobs?title=dev&workMode=REMOTE&page=0&limit=8`

Retorna vagas com filtros aplicados.

#### Resposta:

```json
{
  "page": 0,
  "limit": 8,
  "totalElements": 23,
  "totalPages": 3,
  "isLast": false,
  "content": [
    {
      "id": "95612728-8314-46f5-a203-a08987123a61",
      "title": "Desenvolvedor Full-Stack",
      "location": "São Paulo - SP",
      "workMode": "REMOTE",
      "contractType": "INTERN",
      "salary": 15000.5,
      "companyName": "SilverHand",
      "createdAt": "2025-07-02T18:46:19.670305"
    }
  ]
}
```

---

## 🧪 Testes e Segurança

- Validação de arquivos PDF.
- Proteção contra acesso não autorizado via Spring Security.
- Tokens criptografados com segredo configurável.
- Upload com limite de tamanho e tipo de arquivo.

---
