package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Min(value = 0, message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Price must be positive")
    private ProductSizeDTO productSize;
    @NotBlank(message = "CreatedAt cannot be blank")
    private LocalDateTime createdAt;
    @NotBlank(message = "Subcategory ID cannot be blank")
    private String subcategoryId;
    @NotBlank(message = "Social group ID cannot be blank")
    private String socialGroupId;
    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;

    public Boolean getActive() {
        return isActive;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getSocialGroupId() {
        return socialGroupId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }



    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;
}
