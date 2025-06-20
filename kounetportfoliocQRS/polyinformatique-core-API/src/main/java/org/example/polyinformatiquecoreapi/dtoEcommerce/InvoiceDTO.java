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
    private String id;
    @NotBlank(message = "Order ID cannot be blank")
    private String orderId;
    @NotBlank(message = "Order ID cannot be blank")
    private String custumerId;
    @Min(value = 0, message = "Amount must be positive")
    private double amount;
    @NotBlank(message = "Payment status cannot be blank")
    private String methodePayment;
    @NotBlank(message = "Payment status cannot be blank")
    private Double restMonthlyPayment;
    @NotBlank(message = "Payment status cannot be blank")
    private String paymentStatus;
   @NotBlank(message = "Payment status cannot be blank")
   private String supplierId;
}
