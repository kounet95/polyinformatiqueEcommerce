package org.example.ecpolyquery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LikeProduct {

  @Id
  private String id;
  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private ProductSize product;

  private LocalDateTime createdAt;
}
