
# 👤 CandidateController API

Este documento descreve os endpoints disponíveis para o gerenciamento de candidatos no sistema HireSync.

## 📂 Base URL

```
/candidates
```

---

## 🔍 GET `/candidates`

### Descrição:
Retorna todos os candidatos cadastrados no sistema.

### Permissão:
🔒 Apenas administradores (`ADMIN`).

### Exemplo de Requisição:
```
GET /candidates
```

### Resposta:
```json
[
  {
    "id": "uuid-do-candidato",
    "name": "João da Silva",
    "email": "joao@email.com",
    "course": "Sistemas de Informação",
    "address": "Rua das Flores, 123",
    "cpf": "12345678900",
    "birthDate": "1998-01-15",
    "graduationYear": "2024-12-01",
    "currentlyStudying": true,
    "currentSemester": 6,
    "gender": "Masculino",
    "phone": "11999998888",
    "roles": ["CANDIDATE"]
  }
]
```

---

## 🔍 GET `/candidates/{id}`

### Descrição:
Retorna os dados de um candidato específico a partir do seu UUID.

### Exemplo de Requisição:
```
GET /candidates/uuid-do-candidato
```

### Resposta:
```json
{
  "id": "uuid-do-candidato",
  "name": "João da Silva",
  "email": "joao@email.com",
  "course": "Sistemas de Informação",
  "address": "Rua das Flores, 123",
  "cpf": "12345678900",
  "birthDate": "1998-01-15",
  "graduationYear": "2024-12-01",
  "currentlyStudying": true,
  "currentSemester": 6,
  "gender": "Masculino",
  "phone": "11999998888",
  "roles": ["CANDIDATE"]
}
```

---

## ✏️ PUT `/candidates/{id}`

### Descrição:
Atualiza os dados de um candidato existente.

### Permissão:
🔒 Acessível por `CANDIDATE` (se for o dono) ou `ADMIN`.

### Corpo da Requisição (UpdateCandidateDto):
```json
{
  "name": "João da Silva",
  "phone": "11988887777",
  "address": "Rua Nova, 456",
  "course": "Engenharia da Computação",
  "currentSemester": 7,
  "graduationYear": "2025-12-01"
}
```

### Exemplo de Requisição:
```
PUT /candidates/uuid-do-candidato
```

### Resposta:
```json
{
  "id": "uuid-do-candidato",
  "name": "João da Silva",
  "email": "joao@email.com",
  "course": "Engenharia da Computação",
  "address": "Rua Nova, 456",
  "cpf": "12345678900",
  "birthDate": "1998-01-15",
  "graduationYear": "2025-12-01",
  "currentlyStudying": true,
  "currentSemester": 7,
  "gender": "Masculino",
  "phone": "11988887777",
  "roles": ["CANDIDATE"]
}
```

---

## ❌ DELETE `/candidates/{id}`

### Descrição:
Deleta um candidato com base em seu UUID.

### Permissão:
🔒 Acessível por `CANDIDATE` (se for o dono) ou `ADMIN`.

### Exemplo de Requisição:
```
DELETE /candidates/uuid-do-candidato
```

### Resposta:
```json
{
  "id": "uuid-do-candidato",
  "name": "João da Silva",
  ...
}
```

---

📘 **Notas**:
- Certifique-se de que o candidato não esteja vinculado a nenhuma candidatura ativa antes de deletá-lo.
