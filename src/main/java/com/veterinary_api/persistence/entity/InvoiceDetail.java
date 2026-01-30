package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "invoice_details")
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer quantity;

    @Column(name = "unit_price", precision = 10,scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "sub_total", precision = 10,scale = 2)
    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")

    private Product products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


}
