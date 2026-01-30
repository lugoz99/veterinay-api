package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Table(name = "owners")
@Entity
@Getter
@Setter
@NoArgsConstructor  // Necesario para JPA
@AllArgsConstructor // Útil para Mappers
@Builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String names;

    private String lastNames;

    private String phone;

    private String email;

    @Column(unique = true)
    private String dni;

    private String address;

    private LocalDateTime createdAt;


    @OneToMany(targetEntity = Pet.class,cascade = CascadeType.ALL, mappedBy = "owner")
    // When the relationship is unidirectional, we use JoinColumn here.
    private List<Pet> pets;

    @OneToMany(mappedBy = "invoices", fetch = FetchType.LAZY)
    private List<Invoice> invoices;


}
