package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "eco_invoice_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {
    @Id

    private String id;
    private double amount;
    private String paymentStatus;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orderecommerce orderecommerce;
}
