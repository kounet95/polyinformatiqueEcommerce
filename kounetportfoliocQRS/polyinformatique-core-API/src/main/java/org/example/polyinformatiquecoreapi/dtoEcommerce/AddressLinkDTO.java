package org.example.polyinformatiquecoreapi.dtoEcommerce;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressLinkDTO {
  private Long id;
  private String targetType; // "CUSTOMER", "SUPPLIER", "STOCK", "SHIPPING"
  private String targetId;   // id de l'entité cible
  private String addressId;
}
