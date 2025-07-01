package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;

import java.time.LocalDateTime;
import java.util.Date;

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
  private String rightmage;
  private String frontImage;
  private String backImage;
  private String leftImage;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;
}
