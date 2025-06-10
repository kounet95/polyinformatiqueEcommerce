package org.example.polyinformatiquecoreapi.dtoEcommerce;

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
public class ShippingDTO {
  private String id;
  @NotBlank
  private String orderId;
  @NotBlank
  private String deliveryStatus;
  @NotNull
  private LocalDateTime estimatedDeliveryDate;
  @NotNull
  private LocalDateTime shippingDate;
  @NotNull
  private LocalDateTime createdAt;
  @NotBlank
  private String shippingAddress;
}
