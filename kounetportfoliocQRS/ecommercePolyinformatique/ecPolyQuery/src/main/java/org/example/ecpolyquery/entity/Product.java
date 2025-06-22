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
public class Product {
  @Id
  private String id;
  private String name;
  private String description;
  private boolean isActive;
  private String urlModels;
  @ManyToOne
  @JoinColumn(name = "subcategory_id")
  private Subcategory subcategory;
  @ManyToOne
  @JoinColumn(name = "social_group_id")
  private SocialGroup socialGroup;
  @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true , fetch=FetchType.LAZY)
  @com.fasterxml.jackson.annotation.JsonIgnore
  private List<ProductSize> productSizes;
}
