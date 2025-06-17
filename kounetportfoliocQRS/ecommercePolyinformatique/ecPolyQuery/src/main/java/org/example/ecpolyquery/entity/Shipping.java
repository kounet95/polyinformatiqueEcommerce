package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Shipping {
    @Id
    private String id;
    private OrderStatus deliveryStatus;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime shippingDate;
    @ManyToOne
    @JoinColumn(name = "shippingId")
    private Address addressId;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orderecommerce orderecommerce;
}
