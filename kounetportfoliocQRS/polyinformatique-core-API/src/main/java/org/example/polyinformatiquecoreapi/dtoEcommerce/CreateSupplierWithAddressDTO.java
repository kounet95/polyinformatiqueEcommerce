package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplierWithAddressDTO {

  // --- Données du supplier ---
  private String fullname;
  private String email;

  private String personToContact;

  // --- Données de l'adresse ---
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private String appartment;
  private List<AddressLinkDTO> links;
}
