package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stock {
  @Id
  private String id;
  private double purchasePrice;
  private String designation;
  private double promoPrice;
  private LocalDateTime createdDate;
  private LocalDateTime closedDate;
  @ManyToOne
  @JoinColumn(name = "product_size_id")
  private ProductSize productSize;
  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;
  @ManyToOne
  @JoinColumn(name = "orderecommerceId")
  private Orderecommerce orderecommerceId;
  @ManyToOne
  @JoinColumn(name = "addressId")
  private Address store;
}
