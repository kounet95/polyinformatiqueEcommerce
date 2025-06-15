package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

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
  private double promoPrice;
  private double salePrice;
  private int stockAvailable;

  @ManyToOne
  @JoinColumn(name = "product_size_id")
  private ProductSize productSize;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;
}
