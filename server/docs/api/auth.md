# 🔐 Autenticação - API Endpoints

Este documento descreve todos os endpoints relacionados à autenticação de usuários (candidatos e empresas) na API.

Base URL: `/auth`

---

## ✅ POST `/auth/login`

Autentica um usuário (candidato ou empresa) e retorna os dados do usuário e token JWT via cookie.

### 🔸 Requisição

```json
{
  "email": "user@email.com",
  "password": "senha123"
}
```

### 🔸 Resposta (`200 OK`)

```json
{
  "user": {
    "id": "87955506-3097-4e65-9b2d-5067e7368409",
    "name": "João da Silva",
    "email": "joao@email.com",
    "cpf": "12345678900",
    "course": "Engenharia de Software",
    "address": "Rua A, 123",
    "birthDate": "2000-10-10",
    "graduationYear": "2025-12-01",
    "currentlyStudying": true,
    "currentSemester": 5,
    "gender": "Masculino",
    "phone": "(11) 99999-9999",
    "roles": ["CANDIDATE"]
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

### 🔸 Códigos de Status

- `200 OK`: Autenticado com sucesso.
- `401 Unauthorized`: Credenciais inválidas.

---

## 🧑‍🎓 POST `/auth/register/candidate`

Registra um novo candidato e autentica automaticamente.

### 🔸 Requisição

```json
{
  "name": "João da Silva",
  "email": "joao@email.com",
  "password": "senhaSegura123",
  "cpf": "12345678900",
  "course": "Engenharia da Computação",
  "address": "Rua A, 123",
  "birthDate": "2000-10-10",
  "graduationYear": "2025-12-01",
  "currentlyStudying": true,
  "currentSemester": 5,
  "gender": "Masculino",
  "phone": "(11) 99999-9999"
}
```

### 🔸 Resposta (`201 Created`)

```json
{
  "id": "87955506-3097-4e65-9b2d-5067e7368409",
  "name": "João da Silva",
  "email": "joao@email.com",
  "course": "Engenharia da Computação",
  "address": "Rua A, 123",
  "cpf": "12345678900",
  "birthDate": "2000-10-10",
  "graduationYear": "2025-12-01",
  "currentlyStudying": true,
  "currentSemester": 5,
  "gender": "Masculino",
  "phone": "(11) 99999-9999",
  "roles": ["CANDIDATE"]
}
```

### 🔸 Códigos de Status

- `201 Created`: Registro bem-sucedido.
- `409 Conflict`: Email ou CPF já cadastrado.

---

## 🏢 POST `/auth/register/company`

Registra uma nova empresa e autentica automaticamente.

### 🔸 Requisição

```json
{
  "name": "Tech Solutions",
  "email": "contato@tech.com",
  "password": "senhaForte456",
  "cnpj": "12345678000199",
  "phone": "(11) 4002-8922",
  "website": "https://tech.com",
  "address": "Av. Paulista, 1000",
  "isHeadOffice": true,
  "groupName": "TechGroup"
}
```

### 🔸 Resposta (`201 Created`)

```json
{
  "id": "2fa59c4e-b274-4ef8-bb14-f983edbc9d40",
  "name": "Tech Solutions",
  "email": "contato@tech.com",
  "cnpj": "12345678000199",
  "logoImage": null,
  "phone": "(11) 4002-8922",
  "website": "https://tech.com",
  "address": "Av. Paulista, 1000",
  "isHeadOffice": true,
  "groupName": "TechGroup",
  "roles": ["COMPANY"]
}
```

### 🔸 Códigos de Status

- `201 Created`: Registro bem-sucedido.
- `409 Conflict`: Email ou CNPJ já cadastrado.

---

## 🔓 POST `/auth/logout`

Realiza logout do usuário e remove o cookie de autenticação.

### 🔸 Resposta (`200 OK`)

```json
"Successfully logged out."
```

- O cookie `access_token` será expirado.

---

## 🔍 GET `/auth/check`

Verifica se o usuário está autenticado.

### 🔸 Resposta (`200 OK`)

```json
{
  "id": "...",
  "name": "Usuário Logado",
  "email": "email@exemplo.com",
  ...
}
```

### 🔸 Códigos de Status

- `200 OK`: Usuário autenticado.
- `401 Unauthorized`: Token inválido ou ausente.

---

## 📘 Observações Finais

- JWT é enviado via cookie `HttpOnly`.
- Cookies expiram em 1 hora.
- Senhas são criptografadas antes de serem armazenadas.