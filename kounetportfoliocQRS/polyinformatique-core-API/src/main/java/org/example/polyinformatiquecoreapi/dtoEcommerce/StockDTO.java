package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class StockDTO {
  private String id;
  @NotBlank(message = "Designation cannot be blank")
  private String designation;
  @NotBlank(message = "Product size ID cannot be blank")
  private String productSizeId;
  @NotBlank(message = "Supplier ID cannot be blank")
  private String supplierId;
  @Min(value = 0, message = "Purchase price must be positive")
  private double purchasePrice;
  @Min(value = 0, message = "promoPrice price must be positive")
  private double promoPrice;
  @Min(value = 0, message = "Quantity must be zero or positive")
  private double quantity;
}
