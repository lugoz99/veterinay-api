package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String invoiceNumber;

    private String notes;

    private LocalDateTime invoiceDate;

    @Column(precision = 10,scale = 2)
    private BigDecimal tax;

    @Column(precision = 10,scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @PrePersist
    protected void onCreate() {
        this.invoiceDate = LocalDateTime.now();
    }
}
