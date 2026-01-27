package com.veterinary_api.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;



@Table(name = "owners")
@Entity
@Getter
@Setter
@NoArgsConstructor  // Necesario para JPA
@AllArgsConstructor // Útil para Mappers
@Builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String names;

    private String lastNames;

    private String phone;

    private String email;

    private String dni;

    private String address;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }


}
