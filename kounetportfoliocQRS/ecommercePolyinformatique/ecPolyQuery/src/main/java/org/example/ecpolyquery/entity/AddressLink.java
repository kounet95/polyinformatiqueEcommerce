package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressLink {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String targetType; // "CUSTOMER", "SUPPLIER", "STOCK", "SHIPPING"
  private String targetId;   // id de l'entité cible pour nous permettre de savoir quel entite on utilise

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id")
  private Address address;
}
