package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor

@Setter
public class PurchaseDTO {
    private String id;

    @NotBlank(message = "Supplier ID cannot be blank")
    private String supplierId;

    @NotBlank(message = "CreatedAt cannot be blank")
    private String createdAt;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Min(value = 0, message = "Total must be positive")
    private double total;
}