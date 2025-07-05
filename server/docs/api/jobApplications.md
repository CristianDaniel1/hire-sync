# 📄 Documentação da API - Candidaturas (`/applications`)

Este documento descreve os endpoints disponíveis para gerenciar candidaturas no sistema da plataforma **HireSync**.

---

## 🔐 Autenticação e Autorização

A maioria dos endpoints requer autenticação via token JWT e uso de roles apropriadas: `ADMIN`, `COMPANY`, ou `CANDIDATE`.

---

## 📥 POST `/applications`

Cria uma nova candidatura. Apenas candidatos autenticados podem se candidatar.

**Requer Role:** `CANDIDATE`

**Request Body:**

```json
{
  "jobId": "uuid-da-vaga"
}
```

**Response:** `201 Created`

```json
{
  "id": "uuid-da-candidatura",
  "status": "PENDING",
  "createdAt": "2025-07-02T18:00:00",
  "modifiedAt": "2025-07-02T18:00:00",
  "candidateId": "uuid-candidato",
  "candidateName": "João da Silva",
  "candidateEmail": "joao@email.com",
  "jobId": "uuid-da-vaga",
  "jobTitle": "Dev Backend",
  "companyName": "HireSync Tech"
}
```

---

## 📤 GET `/applications`

Retorna todas as candidaturas registradas no sistema.

**Requer Role:** `ADMIN`

**Response:**

```json
[ {...}, {...} ]
```

---

## 🔍 GET `/applications/{id}`

Busca uma candidatura específica por ID.

**Permissão:** Apenas o candidato responsável, empresa da vaga ou administrador.

**Response:**

```json
{
  "id": "uuid-da-candidatura",
  ...
}
```

---

## 🧑 GET `/applications/candidate/{id}`

Lista todas as candidaturas feitas por um determinado candidato.

**Requer Role:** `ADMIN` ou o próprio `CANDIDATE`.

---

## 🏢 GET `/applications/company/{id}`

Lista todas as candidaturas para as vagas de uma empresa específica.

**Requer Role:** `ADMIN` ou `COMPANY`.

---

## 💼 GET `/applications/job/{id}`

Lista todas as candidaturas associadas a uma vaga específica.

**Requer Role:** `ADMIN` ou `COMPANY`.

---

## 🔄 PUT `/applications/{id}`

Atualiza o status ou informações da candidatura.

**Requer Role:** `ADMIN` ou `COMPANY` (empresa responsável pela vaga).

**Request Body:**

```json
{
  "status": "INTERVIEW"
}
```

**Response:**

```json
{
  "id": "uuid-da-candidatura",
  "status": "INTERVIEW",
  ...
}
```

---

## ❌ DELETE `/applications/{id}`

Exclui uma candidatura.

**Permissão:** `ADMIN`, `CANDIDATE` responsável ou `COMPANY` da vaga.

**Response:** `204 No Content`

---

## ✅ Status Possíveis

- `PENDING` (pendente)
- `REJECTED` (rejeitada)
- `INTERVIEW` (em entrevista)
- `HIRED` (contratado)
- `CANCELLED` (cancelado)

---
