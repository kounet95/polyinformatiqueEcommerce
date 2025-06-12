package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity(name = "eco_orderstatus")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusView {
    @Id

    private String id;

    private String orderId;
    private String barcode;
    private String status;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
