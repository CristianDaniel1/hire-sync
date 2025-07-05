# 📦 Endpoints de Empresas (`/companies`)

Controlador responsável por gerenciar operações relacionadas às empresas, como listagem, detalhamento, atualização e exclusão.

---

## 🔍 GET `/companies`

Retorna todas as empresas cadastradas.

### ✅ Exemplo de Requisição

```
GET http://localhost:8080/companies
```

### ✅ Exemplo de Resposta

```json
[
  {
    "id": "b1234567-89ab-cdef-0123-456789abcdef",
    "name": "Tech Corp",
    "email": "contato@techcorp.com",
    "cnpj": "12345678000100",
    "logoImage": null,
    "phone": "11988887777",
    "website": "https://techcorp.com",
    "address": "Av. Paulista, 1000 - São Paulo",
    "isHeadOffice": true,
    "groupName": "Tech Group",
    "roles": ["COMPANY"]
  }
]
```

---

## 📄 GET `/companies/{id}`

Retorna os dados de uma empresa específica com base no seu UUID.

### 🔐 Acesso: Público

### ✅ Exemplo

```
GET http://localhost:8080/companies/3ddcc809-3c6c-4284-b8f0-93f870652733
```

### ✅ Exemplo de Resposta

```json
{
  "id": "3ddcc809-3c6c-4284-b8f0-93f870652733",
  "name": "SilverHand",
  "email": "vagas@silverhand.com",
  "cnpj": "12345678901234",
  "logoImage": null,
  "phone": "11988887777",
  "website": null,
  "address": "São Paulo - SP",
  "isHeadOffice": false,
  "groupName": "Silver Group",
  "roles": ["COMPANY"]
}
```

---

## 🧾 GET `/companies/group/{group}`

Retorna todas as empresas que pertencem a um grupo específico.

### 🔐 Acesso: Público

### ✅ Exemplo

```
GET http://localhost:8080/companies/group/Silver Group
```

### ✅ Exemplo de Resposta

```json
[
  {
    "id": "3ddcc809-3c6c-4284-b8f0-93f870652733",
    "name": "SilverHand",
    "groupName": "Silver Group",
    "roles": ["COMPANY"],
    "...": "..."
  },
  {
    "id": "9bbcc809-3c6c-4284-b8f0-91f830789712",
    "name": "SilverSoft",
    "groupName": "Silver Group",
    "roles": ["COMPANY"],
    "...": "..."
  }
]
```

---

## ✏️ PUT `/companies/{id}`

Atualiza os dados de uma empresa existente.

### 🔐 Acesso: `COMPANY`, `ADMIN`

### 🔸 Corpo da Requisição

```json
{
  "name": "Nova Empresa",
  "email": "contato@novaempresa.com",
  "phone": "11999998888",
  "website": "https://novaempresa.com",
  "address": "Rua Nova, 123",
  "groupName": "Novo Grupo",
  "isHeadOffice": true
}
```

### ✅ Exemplo de Resposta

```json
{
  "id": "3ddcc809-3c6c-4284-b8f0-93f870652733",
  "name": "Nova Empresa",
  "email": "contato@novaempresa.com",
  "groupName": "Novo Grupo",
  "...": "..."
}
```

---

## 🗑 DELETE `/companies/{id}`

Remove uma empresa do sistema.

### 🔐 Acesso: `COMPANY`, `ADMIN`

### ✅ Exemplo

```
DELETE http://localhost:8080/companies/3ddcc809-3c6c-4284-b8f0-93f870652733
```

### ✅ Exemplo de Resposta

```json
{
  "id": "3ddcc809-3c6c-4284-b8f0-93f870652733",
  "name": "SilverHand",
  "email": "vagas@silverhand.com",
  "...": "..."
}
```

---

📌 **Observação**: o campo `roles` indica o papel da empresa no sistema. Em geral, será `[ "COMPANY" ]`, mas administradores podem visualizar todos os registros.