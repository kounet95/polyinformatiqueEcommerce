package org.example.polyinformatiquecoreapi.dtoEcommerce;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private int appartment; // ou Integer si optionnel

  // Facultatif : tu peux ajouter une liste de liens si tu veux tout gérer d'un coup
  // private List<LinkDTO> links;
}

