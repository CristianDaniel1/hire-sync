# 📘 Diagrama de Classes - HireSync

Este documento descreve as entidades principais do sistema, seus atributos e relacionamentos.

---

## 📊 Diagrama UML (Mermaid)

```mermaid
classDiagram
    class AppUser {
        UUID id
        String name
        String email
        String password
        LocalDateTime createdAt
        LocalDateTime modifiedAt
        Set<Role> roles
    }

    class Candidate {
        UUID id
        String cpf
        String phone
        String course
        boolean currentlyStudying
        int currentSemester
        LocalDate graduationYear
        LocalDate birthDate
        String gender
        String address
    }

    class Resume {
        UUID id
        String summary
        String skills
        String languages
        String certifications
        String githubUrl
        String portfolioUrl
        String linkedinUrl
        String repositoryUrl
        String fileUrl
        LocalDateTime createdAt
        LocalDateTime modifiedAt
    }

    class Company {
        UUID id
        String cnpj
        String logoImage
        String phone
        String website
        String address
        Boolean isHeadOffice
        String groupName
    }

    class Job {
        UUID id
        String title
        String description
        String location
        BigDecimal salary
        WorkMode workMode
        Contract contractType
        String requirements
        String responsibilities
        String courseRequired
        LocalDate graduationYearRequired
        Integer minSemester
        LocalDate expiresAt
        LocalDateTime createdAt
        LocalDateTime modifiedAt
    }

    class JobApplication {
        UUID id
        Status status
        LocalDateTime createdAt
        LocalDateTime modifiedAt
    }

    AppUser "1" -- "1" Candidate : has
    AppUser "1" -- "1" Company : has
    Candidate "1" -- "1" Resume : has
    Candidate "1" -- "many" JobApplication : applies
    Company "1" -- "many" Job : posts
    Job "1" -- "many" JobApplication : receives
    JobApplication "1" -- "1" Candidate : belongs to
    JobApplication "1" -- "1" Job : for
```

---

## 🧩 Enums Utilizados

- **Role**: Enum que representa o papel do usuário (ex: ADMIN, CANDIDATE, COMPANY).
- **WorkMode**: Enum para modalidade de trabalho (ex: REMOTE, HYBRID, ON_SITE).
- **Contract**: Enum para tipo de contrato (ex: INTERNSHIP, FULL_TIME).
- **Status**: Enum para status de aplicação (ex: PENDING, APPROVED, REJECTED).
