package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentFailedEvent {

  private final String orderId;
  private final String reason; // Optionnel, message d'erreur ou raison de l'échec

  // Tu peux ajouter d'autres champs si besoin
}
