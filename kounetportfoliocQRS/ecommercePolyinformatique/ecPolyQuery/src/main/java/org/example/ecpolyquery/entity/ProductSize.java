package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductSize {
  @Id
  private String id;
  @Enumerated(EnumType.STRING)
  private SizeProd size;
  private Double price;
  private Double promoPrice;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;
}
