package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderDTO {
    private String id;
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String customerId;
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String supplierId;
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String createdAt;
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String paymentMethod;
    @Min(value = 0, message = "Total must be positive")
    private double total;
    @NotBlank(message = "Street cannot be blank")
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String shippingId;

}
