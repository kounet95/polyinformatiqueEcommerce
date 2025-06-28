package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressLinkedEvent {
  private String targetType;  // "CUSTOMER", "SUPPLIER", etc.
  private String targetId;
  private String addressId;
}
