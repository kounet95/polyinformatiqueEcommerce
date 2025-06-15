package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseItem {
  @Id
  private String id;

  private int qty;
  private double unitPrice;

  @ManyToOne
  @JoinColumn(name = "purchase_id")
  private Purchase purchase;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
