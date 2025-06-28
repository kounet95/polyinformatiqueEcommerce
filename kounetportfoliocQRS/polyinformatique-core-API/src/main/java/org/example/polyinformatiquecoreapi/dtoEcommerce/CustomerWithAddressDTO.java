package org.example.polyinformatiquecoreapi.dtoEcommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerWithAddressDTO {
  private String firstname;
  private String lastname;
  private String email;
  private String phone;
  private AddressDTO address;
}
