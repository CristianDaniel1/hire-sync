package com.syncteam.hiresync.repositories;

import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByUser(AppUser user);

    List<Company> findByGroupName(String group);

    boolean existsByCnpj(String cnpj);
}
