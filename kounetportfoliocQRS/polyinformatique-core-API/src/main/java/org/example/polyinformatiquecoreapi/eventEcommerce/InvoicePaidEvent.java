package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

@Getter
public class InvoicePaidEvent extends BaseEvent<String> {

  private final InvoiceDTO invoiceDTO;
  private final String paymentIntentId;
  private final String sessionId;

  public InvoicePaidEvent(String id, InvoiceDTO invoiceDTO,
                          String paymentIntentId, String sessionId) {
    super(id);
    this.invoiceDTO = invoiceDTO;
    this.paymentIntentId = paymentIntentId;
    this.sessionId = sessionId;
  }
}
