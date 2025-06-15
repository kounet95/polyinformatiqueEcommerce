package org.example.polyinformatiquecoreapi.dtoEcommerce;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductSizeDTO {
  private String id;

  @NotBlank(message = "Category name cannot be blank")
  @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")

  private String name;

  private sizeProd sizeProd;
  private String prodId;
  private Double price;
  private int stock_available;
  private Double pricePromo;

}
