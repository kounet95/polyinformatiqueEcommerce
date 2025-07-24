package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class payementDTO {

  private String id;

  private String paymentIntentId; // Stripe PaymentIntent ID

  private String sessionId; // Stripe Checkout Session ID

  private double amount;

  private String currency;

  private String status; // e.g. 'pending', 'succeeded', 'failed', 'refunded'

  private LocalDateTime createdAt;
  private String invoice; // la facture payée
}
