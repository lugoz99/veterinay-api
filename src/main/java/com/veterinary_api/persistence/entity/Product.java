package com.veterinary_api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 50)
    private String presentation;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "minimum_stock", nullable = false)
    private Integer minimumStock = 10;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(length = 100)
    private String provider;

    @Column(name = "requires_prescription")
    private Boolean requiresPrescription = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<InvoiceDetail> invoiceDetails;

    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY)
    private List<Treatment> treatments;

}
