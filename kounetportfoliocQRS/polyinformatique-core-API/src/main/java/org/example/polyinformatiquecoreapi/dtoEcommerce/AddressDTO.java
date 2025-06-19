package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
  private Long id;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  private String customer;
  private String store;
  private String supplier;
  private String shipping;
}
