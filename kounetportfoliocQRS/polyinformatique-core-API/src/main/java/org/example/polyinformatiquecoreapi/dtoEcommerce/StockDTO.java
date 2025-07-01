package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class StockDTO {
  private String id;
  @NotBlank(message = "Designation cannot be blank")
  private String productSizeId;
  @NotBlank(message = "Supplier ID cannot be blank")
  private String supplierId;
  @NotNull(message = "Purchase price must not be null")
  @Min(value = 0, message = "Purchase price must be positive")
  private Double purchasePrice;

  @NotNull(message = "Promo price must not be null")
  @Min(value = 0, message = "Promo price must be positive")
  private Double promoPrice;

  @NotNull(message = "Quantity must not be null")
  @Min(value = 0, message = "Quantity must be zero or positive")
  private Double quantity;

  private String supplyId;
}
