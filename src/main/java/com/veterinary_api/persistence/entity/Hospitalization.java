package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospitalizations")
@Getter
@Setter
@NoArgsConstructor
public class Hospitalization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    @Column(name = "admission_date", nullable = false)
    private LocalDateTime admissionDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Column(name = "admission_diagnosis", columnDefinition = "TEXT", nullable = false)
    private String admissionDiagnosis;

    @Column(name = "discharge_date")
    private LocalDateTime dischargeDate;

    @Column(name = "discharge_diagnosis", columnDefinition = "TEXT")
    private String dischargeDiagnosis;

    @Column(name = "room_number", precision = 10,scale = 2)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HospitalizationStatus status = HospitalizationStatus.ADMITTED;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Column(columnDefinition = "TEXT")
    private String observations;

    public enum HospitalizationStatus {
        ADMITTED,    // Internado actualmente
        DISCHARGED,  // Dado de alta
        TRANSFERRED, // Trasladado a otro centro
        DECEASED     // Fallecido durante la internación
    }

}
