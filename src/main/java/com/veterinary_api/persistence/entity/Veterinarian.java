package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "veterinarians")
@Entity
@Getter
@Setter
@NoArgsConstructor  // Necesario para JPA
@AllArgsConstructor // Útil para Mappers
@Builder
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String names;

    private String lastNames;

    private String phone;

    public String license;

    public String specialty;

    private String email;

    private String dni;

    private String address;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "veterinarian",fetch = FetchType.LAZY)
    private List<Hospitalization> hospitalizations;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
