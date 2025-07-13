package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
@Getter
public class PayInvoiceCommand extends BaseCommand<String> {

private final InvoiceDTO invoiceDTO;
private final String paymentIntentId;
private final String sessionId;

    public PayInvoiceCommand(String id, InvoiceDTO invoiceDTO, String paymentIntentId,
                             String sessionId) {
      super(id);
      this.invoiceDTO = invoiceDTO;
      this.paymentIntentId = paymentIntentId;
      this.sessionId = sessionId;
    }
  }
