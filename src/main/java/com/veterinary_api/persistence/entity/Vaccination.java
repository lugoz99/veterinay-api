package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "vaccinations")
@Getter
@Setter
@NoArgsConstructor
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne: Una mascota recibe muchas vacunas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // Relación ManyToOne: Un veterinario aplica la vacuna
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    @Column(name = "vaccine_name", nullable = false, length = 100)
    private String vaccineName;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(length = 50)
    private String lot; // lote

    @Column(length = 100)
    private String laboratory;

    @Column(name = "next_dose_date")
    private LocalDate nextDoseDate;

    @Column(columnDefinition = "TEXT")
    private String observations;
}
