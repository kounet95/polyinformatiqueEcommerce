package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProductDTO {
  private String id;

  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
  private String name;
  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 1000, message = "Street must be between 2 and 1000 characters")
  private String description;
  private List<String> productSizesId;
  @NotNull(message = "CreatedAt cannot be null")
  private LocalDateTime createdAt;
  @NotNull(message = "models  cannot be blank")
  private String models;
  @NotBlank(message = "Subcategory ID cannot be blank")
  private String subcategoryId;
  @NotBlank(message = "Social group ID cannot be blank")
  private String socialGroupId;
  @NotNull(message = "isActive cannot be null")
  private Boolean isActive;
}
