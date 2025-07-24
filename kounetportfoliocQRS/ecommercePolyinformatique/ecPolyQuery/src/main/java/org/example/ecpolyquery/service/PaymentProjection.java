package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Invoice;
import org.example.ecpolyquery.entity.Payment;
import org.example.ecpolyquery.repos.InvoiceRepository;
import org.example.ecpolyquery.repos.PaymentRepository;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoicePaidEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentProjection {

  private final PaymentRepository paymentRepository;
  private final InvoiceRepository invoiceRepository;

  @EventHandler
  public void on(InvoicePaidEvent event) {
    Invoice invoice = invoiceRepository.findById(event.getId()).orElseThrow();

    Payment payment = Payment.builder()
      .id(UUID.randomUUID().toString())
      .paymentIntentId(event.getPaymentIntentId())
      .sessionId(event.getSessionId()) // idem
      .amount(invoice.getAmount())
      .currency("eur")
      .status("succeeded")
      .createdAt(LocalDateTime.now())
      .invoice(invoice)
      .build();

    paymentRepository.save(payment);
  }
}

