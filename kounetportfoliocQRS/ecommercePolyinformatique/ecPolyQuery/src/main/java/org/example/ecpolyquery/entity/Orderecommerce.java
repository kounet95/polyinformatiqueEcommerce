package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orderecommerce {
  @Id
  private String id;
  private LocalDateTime createdAt;
  private OrderStatus orderStatus;
  private String barcode;
  private String paymentMethod;
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
  @ManyToOne
  @JoinColumn(name = "supplierId")
  private Supplier supplierId;
  private String currency;
  private BigDecimal total;
  @OneToOne(mappedBy = "orderecommerce",
    cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Shipping shipping;
  @OneToMany(mappedBy = "orderecommerce",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY)
  private List<OrderLine> orderLines;
}
