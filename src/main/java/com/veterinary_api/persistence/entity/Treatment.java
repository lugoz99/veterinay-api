package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "treatments")
@Getter
@Setter
@NoArgsConstructor
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private MedicalConsultation consultation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Product medication;

    @Column(nullable = false, length = 100)
    private String dosage;

    @Enumerated(EnumType.STRING) // Guarda el texto en la DB para mayor claridad
    @Column(name = "administration_route", nullable = false)
    private AdministrationRoute administrationRoute;

    @Column(nullable = false, length = 100)
    private String frequency;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_quantity", nullable = false, precision = 8, scale = 2)
    private BigDecimal totalQuantity;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TreatmentStatus status = TreatmentStatus.ACTIVE;

    public enum AdministrationRoute {
        ORAL,            // Vía oral
        INTRAVENOUS,     // Intravenosa
        INTRAMUSCULAR,   // Intramuscular
        SUBCUTANEOUS,    // Subcutánea
        TOPICAL,         // Tópica (piel)
        OPHTHALMIC,      // Oftálmica (ojos)
        OTIC             // Ótica (oídos)
    }

    public enum TreatmentStatus {
        ACTIVE,          // En curso
        COMPLETED,       // Finalizado
        SUSPENDED,       // Suspendido por el médico
        CANCELLED        // Cancelado antes de iniciar
    }

}
