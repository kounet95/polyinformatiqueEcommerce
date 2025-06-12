package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity(name = "eco_shopping_purchase")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Shipping {
    @Id

    private String id;

    private String deliveryStatus;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime shippingDate;
    private String shippingAddress;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orderecommerce orderecommerce;
}
