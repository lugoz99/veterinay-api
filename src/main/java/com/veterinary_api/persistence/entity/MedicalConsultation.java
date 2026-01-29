package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_consultations")
@Getter
@Setter
@NoArgsConstructor
public class MedicalConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "current_weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal currentWeight;

    @Column(precision = 4, scale = 2)
    private BigDecimal temperature;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String symptoms;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "next_visit")
    private LocalDate nextVisit;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // One-to-One relationship: One consultation belongs to ONE specific appointment
    // fetch = LAZY: Doesn't load the appointment automatically, only when you access it
    // unique = true: Ensures that an appointment can only have ONE medical consultation
    // This entity IS THE OWNER of the relationship (has the FK appointment_id in DB)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Convenience methods to access appointment data without manually navigating
    public Pet getPet() {
        return appointment != null ? appointment.getPet() : null;
    }

    public Veterinarian getVeterinarian() {
        return appointment != null ? appointment.getVeterinarian() : null;
    }

    public LocalDateTime getConsultationDate() {
        return appointment != null ? appointment.getAppointmentDate() : null;
    }
}