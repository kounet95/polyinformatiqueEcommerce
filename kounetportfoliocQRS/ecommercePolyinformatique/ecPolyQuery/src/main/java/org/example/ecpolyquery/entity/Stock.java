package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "eco_stock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stock {
    @Id

    private String id;

    private double purchasePrice;
    private double promoPrice;
    private double salePrice;
    private int stockAvailable;

    private ProductSize productSize;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}

