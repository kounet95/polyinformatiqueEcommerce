package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class InvoiceGeneratedEvent extends BaseEvent<String> {
    private final InvoiceDTO invoiceDTO;

    public InvoiceGeneratedEvent(String id, InvoiceDTO invoiceDTO) {
        super(id);
        this.invoiceDTO = invoiceDTO;
    }

    public InvoiceDTO getInvoiceDTO() {
        return invoiceDTO;
    }
}
