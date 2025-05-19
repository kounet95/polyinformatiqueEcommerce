package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Size cannot be blank")
    private String size;

    @NotBlank(message = "Product ID cannot be blank")
    private String productId;
}