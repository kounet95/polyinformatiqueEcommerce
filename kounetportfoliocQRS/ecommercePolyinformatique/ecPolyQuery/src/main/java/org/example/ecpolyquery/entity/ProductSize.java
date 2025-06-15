package org.example.ecpolyquery.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductSize {
  @Id
  private String id;

  private SizeProduct size;
  private Double price;
  private Double promoPrice;
  private Integer stockAvailable;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}

