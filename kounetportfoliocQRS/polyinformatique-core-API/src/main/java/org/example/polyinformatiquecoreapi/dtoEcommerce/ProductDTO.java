package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductDTO {
  private String id;

  @NotBlank(message = "Product name cannot be blank")
  private String name;
  @NotBlank(message = "Description cannot be blank")
  private String description;
  private List<ProductSizeDTO> productSizes;
  @NotNull(message = "CreatedAt cannot be null")
  private LocalDateTime createdAt;

  private String models;
  @NotBlank(message = "Subcategory ID cannot be blank")
  private String subcategoryId;
  @NotBlank(message = "Social group ID cannot be blank")
  private String socialGroupId;
  @NotNull(message = "isActive cannot be null")
  private Boolean isActive;
}
