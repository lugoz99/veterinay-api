package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "appointments")
@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    // Uso de Enum para el Tipo
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AppointmentType type;

    // Uso de Enum para el Estado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String observations;

    public enum AppointmentType {
        CONSULTATION,    // Consulta general
        VACCINATION,     // Vacunación
        SURGERY,         // Cirugía
        DEWORMING,       // Desparasitación
        EMERGENCY,       // Urgencias
        LAB_TEST,        // Exámenes de laboratorio
        AESTHETIC        // Estética/Peluquería
    }

    public enum AppointmentStatus {
        PENDING,         // Pendiente
        CONFIRMED,       // Confirmada por el cliente
        COMPLETED,       // Finalizada
        CANCELLED,       // Cancelada
        NO_SHOW          // El cliente no asistió
    }

}
