package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;

/**
 * Event emitted when an invoice is created
 */
@Getter
public class InvoiceCreatedEvent {
    private final String id;
    private final InvoiceDTO invoiceDTO;

    public InvoiceCreatedEvent(String id, InvoiceDTO invoiceDTO) {
        this.id = id;
        this.invoiceDTO = invoiceDTO;
    }
}