package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {
  @Id
  private String id;
  private double amount;
  private double restPayment;
  private String paymentStatus;
  private String paymentMethod;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Orderecommerce orderecommerce;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;
}
