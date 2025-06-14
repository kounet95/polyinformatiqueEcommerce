package org.example.polyinformatiquecoreapi.dtoEcommerce;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductSizeDTO {
  private String id;

  private SizeProd sizeProd;
  @NotBlank(message = "prodId cannot be blank")
  private String prodId;
  @NotBlank(message = "prodId cannot be blank")
  private Double price;
  @NotBlank(message = "prodId cannot be blank")
  private int stock_available;
  @NotBlank(message = "prodId cannot be blank")
  private Double pricePromo;
  @NotBlank(message = "prodId cannot be blank")
  private String imageUrl;
}
