# 📄 Documentação da API - Currículos (`/resumes`)

Esta seção descreve os endpoints da API responsáveis por gerenciar currículos de candidatos na plataforma **HireSync**.

---

## 📚 GET `/resumes`

Retorna todos os currículos cadastrados no sistema.

**Permissão:** `ADMIN`

### ✅ Exemplo:

```

GET /resumes

```

### 📦 Exemplo de Resposta:

```json
[
  {
    "id": "a9f123bc-1111-4ac2-bd65-c9b0a65cdef0",
    "summary": "Desenvolvedor Full Stack com experiência em Java e React",
    "skills": "Java, Spring Boot, React, PostgreSQL",
    "languages": "Inglês, Português",
    "certifications": "Oracle Certified Java Programmer",
    "githubUrl": "https://github.com/johndoe",
    "portfolioUrl": "https://johndoe.dev",
    "linkedinUrl": "https://linkedin.com/in/johndoe",
    "repositoryUrl": "https://github.com/johndoe/projects",
    "fileUrl": "/files/resumes/johndoe_resume.pdf",
    "createdAt": "2025-07-01T10:00:00",
    "modifiedAt": "2025-07-02T14:30:00"
  }
]
```

---

## 🔍 GET `/resumes/{id}`

Retorna os dados de um currículo específico.

### ✅ Exemplo:

```
GET /resumes/a9f123bc-1111-4ac2-bd65-c9b0a65cdef0
```

---

## 👤 GET `/resumes/candidate/{id}`

Retorna o currículo de um candidato a partir do seu ID.

### ✅ Exemplo:

```
GET /resumes/candidate/7e43217f-d1a3-4c7e-8921-56e7f214ccf0
```

---

## ➕ POST `/resumes`

Cria um novo currículo no sistema para o candidato logado.

**Permissão:** `CANDIDATE`

### ✅ Exemplo de Requisição:

```json
{
  "summary": "Sou um desenvolvedor com interesse em backend.",
  "skills": "Java, Spring Boot, PostgreSQL",
  "languages": "Português, Inglês",
  "certifications": "Certificação Java SE 11",
  "githubUrl": "https://github.com/usuario",
  "portfolioUrl": "https://usuario.dev",
  "linkedinUrl": "https://linkedin.com/in/usuario",
  "repositoryUrl": "https://github.com/usuario/repositorio"
}
```

---

## ✏️ PUT `/resumes/{id}`

Atualiza os dados de um currículo.

**Permissão:** `CANDIDATE`

### ✅ Exemplo:

```
PUT /resumes/a9f123bc-1111-4ac2-bd65-c9b0a65cdef0
```

**Body:**

```json
{
  "summary": "Atualizei meu resumo com novas informações.",
  "skills": "Java, Spring Boot, AWS",
  "languages": "Português, Inglês avançado",
  "certifications": "AWS Certified Developer",
  "githubUrl": "https://github.com/novousuario",
  "portfolioUrl": "https://novousuario.dev",
  "linkedinUrl": "https://linkedin.com/in/novousuario",
  "repositoryUrl": "https://github.com/novousuario/projetos"
}
```

---

## 🗑️ DELETE `/resumes/{id}`

Remove um currículo.

**Permissão:** `CANDIDATE` ou `ADMIN`

### ✅ Exemplo:

```
DELETE /resumes/a9f123bc-1111-4ac2-bd65-c9b0a65cdef0
```

**Resposta:** `204 No Content`

---

## 📎 POST `/resumes/upload`

Realiza o **upload de um arquivo PDF** associado ao currículo do candidato.

**Permissão:** `CANDIDATE`

### ✅ Requisição Multipart:

```
POST /resumes/upload
Content-Type: multipart/form-data
```

**Body:**

- `file`: arquivo `.pdf` (máx. 5MB)

### 📦 Exemplo de Resposta:

```json
{
  "id": "a9f123bc-1111-4ac2-bd65-c9b0a65cdef0",
  "fileUrl": "/files/resumes/usuario_1751234567981.pdf",
  "createdAt": "2025-07-03T09:12:45",
  "modifiedAt": "2025-07-03T09:12:45"
}
```

---

## ℹ️ Observações

- A URL do currículo (`fileUrl`) pode ser acessada diretamente pelo frontend via:

  ```
  http://localhost:8080/files/resumes/<nome-do-arquivo>.pdf
  ```

- Regras de upload:

  - Somente arquivos `.pdf`
  - Tamanho máximo: 5MB
  - O envio sobrescreve o arquivo anterior (caso exista)
