package org.example.ecpolyquery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  private Double purchasePrice;
  private Double promoPrice;
  private double quantity;
  private LocalDateTime createdDate;
  private LocalDateTime closedDate;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "supply_id")
  private Supply supply;
  @ManyToOne
  @JoinColumn(name = "product_size_id")
  private ProductSize productSize;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  @JsonIgnore
  private Supplier supplier;

  @ManyToOne
  @JoinColumn(name = "orderecommer ceId")
  private Orderecommerce orderecommerceId;

}
