package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  private String id;

  @NotBlank(message = "Customer ID cannot be blank")
  @Size(min = 2, max = 255, message = "Customer ID must be between 2 and 255 characters")
  private String customerEmail;

  @Size(min = 2, max = 255, message = "Supplier ID must be between 2 and 255 characters")
  private String supplierId;

  @NotBlank(message = "Currency cannot be blank")
  @Size(min = 2, max = 255, message = "Currency must be between 2 and 255 characters")
  private String currency;

  @NotBlank(message = "Creation date cannot be blank")
  @Size(min = 2, max = 255, message = "Creation date must be between 2 and 255 characters")
  private String createdAt;

  @NotBlank(message = "Payment method cannot be blank")
  @Size(min = 2, max = 255, message = "Payment method must be between 2 and 255 characters")
  private String paymentMethod;

  @Min(value = 0, message = "Total must be positive")
  private double total;

  @NotBlank(message = "Shipping ID cannot be blank")
  @Size(min = 2, max = 255, message = "Shipping ID must be between 2 and 255 characters")
  private String shippingId;

  // Liste des lignes de commande
  private List<OrderLineDTO> orderLines;
}
