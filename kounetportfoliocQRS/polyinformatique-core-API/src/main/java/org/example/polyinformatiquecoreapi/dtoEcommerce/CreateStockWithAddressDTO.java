package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
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
public class CreateStockWithAddressDTO {

  //---les donne pour stock--------

  private String designation;

  private String productSizeId;

  private String supplierId;

  private double purchasePrice;

  private double promoPrice;

  private double quantity;


  // --- Données de l'adresse ---
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  private List<AddressLinkDTO> links;
}
