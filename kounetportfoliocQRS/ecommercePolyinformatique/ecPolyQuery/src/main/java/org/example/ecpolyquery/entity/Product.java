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
  private LocalDateTime createdAt;
  private boolean isActive;
  private String urlModels;
  @ManyToOne
  @JoinColumn(name = "subcategory_id")
  private Subcategory subcategory;
  @ManyToOne
  @JoinColumn(name = "social_group_id")
  private SocialGroup socialGroup;

}
