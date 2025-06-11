package com.example.quotation.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationEntity {

    @Id
    private String id;

    private String customerName;

    private String currency;

    private double totalPrice;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotationItemEntity> items;
}