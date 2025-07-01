package org.example.polyinformatiquecoreapi.dtoEcommerce;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProductSizeDTO {
  private String id;

  private SizeProd sizeProd;
  @NotBlank(message = "prodId cannot be blank")
  private String prodId;
  @NotNull(message = "Le prix est obligatoire")
  @Min(value = 0, message = "Le prix doit être positif")
  private Double price;
  @NotNull(message = "Le prix est obligatoire")
  @Min(value = 0, message = "Le prix doit être positif")
  private Double pricePromo;
  @NotNull(message = "prodId cannot be blank")
  private String frontUrl;
  private String backUrl;
  private String leftUrl;
  private String rightUrl;
}
