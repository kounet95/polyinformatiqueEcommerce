package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
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
  private String orderStatus;
  private String paymentMethod;
  private double total;
  private String barcode;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "orderecommerce", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderLine> lines;

  @OneToOne(mappedBy = "orderecommerce", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Invoice invoice;

  @OneToOne(mappedBy = "orderecommerce", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Shipping shipping;
}
