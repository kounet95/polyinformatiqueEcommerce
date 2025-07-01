package org.example.polyinformatiquecoreapi.dtoEcommerce;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerWithAddressDTO {
  // --- Données du client ---
  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  // --- Données de l'adresse ---
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private String appartment;
   private List<AddressLinkDTO> links;
}

