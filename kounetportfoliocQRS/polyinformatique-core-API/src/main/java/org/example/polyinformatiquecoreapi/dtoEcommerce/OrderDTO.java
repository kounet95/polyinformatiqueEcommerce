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
public class OrderDTO {
    private String id;

    @NotBlank(message = "Customer ID cannot be blank")
    private String customerId;

    @NotBlank(message = "CreatedAt cannot be blank")
    private String createdAt;

    @NotBlank(message = "Order status cannot be blank")
    private String orderStatus;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;

    @Min(value = 0, message = "Total must be positive")
    private double total;

    public String getBarcode() {
        return barcode;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getId() {
        return id;
    }

    @NotBlank(message = "Barcode cannot be blank")
    private String barcode;
}