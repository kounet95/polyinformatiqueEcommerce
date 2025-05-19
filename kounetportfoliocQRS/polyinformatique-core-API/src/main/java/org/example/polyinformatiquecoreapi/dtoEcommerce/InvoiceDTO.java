package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class InvoiceDTO {
    @NotBlank(message = "Invoice ID cannot be blank")
    private String invoiceId;

    @NotBlank(message = "Order ID cannot be blank")
    private String orderId;

    @Min(value = 0, message = "Amount must be positive")
    private double amount;

    @NotBlank(message = "Payment status cannot be blank")
    private String paymentStatus;
}