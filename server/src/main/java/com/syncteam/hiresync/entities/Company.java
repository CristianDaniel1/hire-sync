package com.syncteam.hiresync.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "companies", schema = "public")
@Data
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "logo_image", length = 200)
    private String logoImage;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "website", length = 150)
    private String website;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "is_head_office", nullable = false)
    private Boolean isHeadOffice;

    @Column(name = "group_name", length = 100)
    private String groupName;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    public Company() {}
}
