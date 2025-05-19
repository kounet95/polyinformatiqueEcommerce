package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;

public class GenerateInvoiceCommand extends BaseCommand<String> {
    private final InvoiceDTO invoiceDTO;

    public GenerateInvoiceCommand(String id, InvoiceDTO invoiceDTO) {
        super(id);
        this.invoiceDTO = invoiceDTO;
    }

    public InvoiceDTO getInvoiceDTO() {
        return invoiceDTO;
    }
}
