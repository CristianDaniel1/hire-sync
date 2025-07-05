# 📄 Documentação da API - Vagas (`/jobs`)

Esta seção cobre os endpoints relacionados à criação, visualização, edição, exclusão e relatórios de **vagas de emprego**.

---

## 🔍 GET `/jobs`

Lista todas as vagas com filtros opcionais e suporte a paginação.

**Query Parameters (opcionais):**

- `title`: filtro pelo título da vaga
- `location`: filtro pela localização
- `workMode`: filtro pelo modo de trabalho (`PRESENTIAL`, `REMOTE`, `HYBRID`)
- `page`: número da página (padrão: 0)
- `limit`: quantidade por página (padrão: 12)

### ✅ Exemplo de Requisição:

```

GET http://localhost:8080/jobs?title=Java&location=SP&workMode=REMOTE&page=0&limit=8

```

### 📦 Exemplo de Resposta:

```json
{
  "page": 0,
  "limit": 8,
  "totalElements": 2,
  "totalPages": 1,
  "isLast": true,
  "content": [
    {
      "id": "a5f31267-1234-4d3b-b1d2-abc123456789",
      "title": "Desenvolvedor Java Pleno",
      "description": "Desenvolver e manter APIs REST com Spring Boot.",
      "location": "São Paulo - SP",
      "salary": 12000.0,
      "workMode": "REMOTE",
      "contractType": "FULL_TIME",
      "requirements": "Experiência com Java, Spring, JPA.",
      "responsibilities": "Desenvolver novas funcionalidades, revisar código.",
      "courseRequired": "Ciência da Computação",
      "graduationYearRequired": null,
      "minSemester": 4,
      "expiresAt": "2025-12-31",
      "createdAt": "2025-07-01T10:00:00",
      "modifiedAt": "2025-07-01T10:00:00",
      "companyId": "123e4567-e89b-12d3-a456-426614174000",
      "companyName": "Tech Ltda",
      "website": "https://tech.com.br",
      "companyLogo": "https://tech.com.br/logo.png",
      "address": "Rua X, São Paulo"
    }
  ]
}
```

---

## 🏢 GET `/jobs/company/{id}`

Lista todas as vagas publicadas por uma empresa.

**Requer Role:** `COMPANY`

### ✅ Exemplo:

```
GET /jobs/company/123e4567-e89b-12d3-a456-426614174000
```

---

## 🔎 GET `/jobs/{id}`

Retorna os detalhes de uma vaga específica.

### ✅ Exemplo:

```
GET /jobs/a5f31267-1234-4d3b-b1d2-abc123456789
```

---

## ➕ POST `/jobs`

Cria uma nova vaga.

**Requer Role:** `COMPANY`

**Request Body:**

```json
{
  "title": "Desenvolvedor Front-end",
  "description": "Desenvolvimento com React.js",
  "location": "Remoto",
  "salary": 8000,
  "workMode": "REMOTE",
  "contractType": "INTERN",
  "requirements": "Conhecimentos em React, TypeScript",
  "responsibilities": "Desenvolver interfaces e colaborar com o time",
  "courseRequired": "Sistemas de Informação",
  "graduationYearRequired": null,
  "minSemester": 2,
  "expiresAt": "2026-06-30"
}
```

**Response:** `201 Created`

```json
{
  "id": "uuid-gerado",
  "title": "Desenvolvedor Front-end",
  ...
}
```

---

## ✏️ PUT `/jobs/{id}`

Atualiza uma vaga existente.

**Requer Role:** `COMPANY`

**Exemplo de Corpo da Requisição:** igual ao de criação (`POST`).

---

## 🗑️ DELETE `/jobs/{id}`

Exclui uma vaga.

**Requer Role:** `COMPANY` ou `ADMIN`

**Response:** `204 No Content`

---

## 📊 GET `/jobs/report/company/{id}`

Gera um relatório com o número de candidaturas por vaga da empresa.

**Requer Role:** `COMPANY` ou `ADMIN`

### ✅ Exemplo de Resposta:

```json
[
  {
    "id": "job-id-1",
    "title": "Dev Backend",
    "location": "São Paulo",
    "expiresAt": "2025-12-31",
    "totalApplications": 10
  },
  {
    "id": "job-id-2",
    "title": "Dev Frontend",
    "location": "Remoto",
    "expiresAt": "2025-11-15",
    "totalApplications": 5
  }
]
```

---

## 📊 GET `/jobs/report/group/{group}`

Retorna o relatório consolidado por grupo de empresas (ex: "Grupo XPTO").

**Requer Role:** `COMPANY` ou `ADMIN`

### ✅ Exemplo de Requisição:

```
GET /jobs/report/group/xpto
```

---

### ✅ Exemplo de Resposta:

```json
[
  {
    "id": "job-id-3",
    "title": "Tech Lead",
    "location": "Híbrido",
    "expiresAt": "2025-12-01",
    "totalApplications": 7
  }
]
```

---

### ℹ️ Observações

- Os campos `createdAt`, `modifiedAt`, e `expiresAt` seguem o formato ISO 8601.
- O campo `salary` é decimal com precisão.
- Os valores de `workMode` são: `REMOTE`, `HYBRID`, `PRESENTIAL`.
- Os valores de `contractType` são: `FULL_TIME`, `PART_TIME`, `INTERN`, etc.
