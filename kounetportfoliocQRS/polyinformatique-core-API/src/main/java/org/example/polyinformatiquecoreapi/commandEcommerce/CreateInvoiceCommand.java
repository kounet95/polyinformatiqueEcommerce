package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;

public class CreateInvoiceCommand extends BaseCommand<String> {
    private InvoiceDTO invoiceDTO;

    public CreateInvoiceCommand(String id, InvoiceDTO invoiceDTO) {
        super(id);
        this.invoiceDTO = invoiceDTO;
    }

    public InvoiceDTO getInvoiceDTO() {
        return invoiceDTO;
    }
}