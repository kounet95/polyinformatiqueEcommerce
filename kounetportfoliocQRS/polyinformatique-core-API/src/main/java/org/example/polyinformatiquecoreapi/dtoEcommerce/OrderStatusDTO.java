package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class OrderStatusDTO {
    private String id;

    @NotBlank(message = "Order ID cannot be blank")
    private String orderId;

    @NotBlank(message = "Barcode cannot be blank")
    private String barcode;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "UpdatedAt cannot be blank")
    private String updatedAt;

    @NotBlank(message = "Customer ID cannot be blank")
    private String customerId;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;
}
