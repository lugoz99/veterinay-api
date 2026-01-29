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

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String observations;

    public enum AppointmentType {
        CONSULTATION,
        VACCINATION,
        SURGERY,
        DEWORMING,
        EMERGENCY,
        LAB_TEST,
        AESTHETIC
    }

    public enum AppointmentStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        NO_SHOW
    }

    // Many-to-One relationship: Many appointments can belong to one pet
    // fetch = LAZY: Doesn't load the pet automatically, only when you access it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // Many-to-One relationship: Many appointments can be handled by one veterinarian
    // fetch = LAZY: Doesn't load the veterinarian automatically, only when you access it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    // One-to-One relationship: One appointment can have ONE medical consultation (optional)
    // mappedBy: Indicates that Appointment is NOT the owner of the relationship, MedicalConsultation is
    // cascade: If you delete the appointment, the associated consultation is also deleted
    // orphanRemoval: If you unassign the consultation from the appointment, it's automatically deleted
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalConsultation medicalConsultation;
}