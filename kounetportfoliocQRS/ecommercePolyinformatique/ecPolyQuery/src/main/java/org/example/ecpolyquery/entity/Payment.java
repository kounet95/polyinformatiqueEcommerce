package org.example.ecpolyquery.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

  @Id
  private String id; // id interne, ou tu peux utiliser le paymentIntentId Stripe directement

  private String paymentIntentId; // Stripe PaymentIntent ID

  private String sessionId; // Stripe Checkout Session ID

  private double amount;

  private String currency;

  private String status; // e.g. 'pending', 'succeeded', 'failed', 'refunded'

  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private Invoice invoice; // la facture payée

}
