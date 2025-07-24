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
public class InvoiceDTO {

    private String id;
  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String orderId;
  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String custumerEmal;
  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private double amount;
   @NotBlank(message = "Street cannot be blank")
   @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String methodePayment;
   @NotBlank(message = "Street cannot be blank")
   @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private Double restMonthlyPayment;
    @NotBlank(message = "Street cannot be blank")
   @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String paymentStatus;
   @NotBlank(message = "Street cannot be blank")
   @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
   private String supplierId;
}
