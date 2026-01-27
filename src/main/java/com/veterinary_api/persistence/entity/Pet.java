package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor  // Necesario para JPA
@AllArgsConstructor // Útil para Mappers
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String gender;
    private String species;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    private String color;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "url_image")
    private String urlImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "neutered_status")
    private NeuteredStatus neuteredStatus;

    public enum NeuteredStatus {
        YES, NO, NOT_APPLICABLE, UNKNOWN
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    // El Mapper ignorará esto si no existe un campo "age" en la BD
    public int getAge(){
        if(dateBirth == null) return 0;
        return Period.between(dateBirth, LocalDate.now()).getYears();
    }
}
