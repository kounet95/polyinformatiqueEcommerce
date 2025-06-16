package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "eco_orderline")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderLine {
    @Id
    private String id;
    private int qty;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orderecommerce orderecommerce;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Stock stockId;
}
