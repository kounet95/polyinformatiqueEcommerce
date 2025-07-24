package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Invoice;
import org.example.ecpolyquery.query.GetAllInvoicesQuery;
import org.example.ecpolyquery.query.GetInvoiceByIdQuery;
import org.example.ecpolyquery.repos.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceQueryHandler {

    private final InvoiceRepository invoiceRepository;

    @QueryHandler
    public List<Invoice> on(GetAllInvoicesQuery query) {
        log.debug("Handling GetAllInvoicesQuery");
        return invoiceRepository.findAll();
    }

    @QueryHandler
    public Invoice on(GetInvoiceByIdQuery query) {
        log.debug("Handling GetInvoiceByIdQuery: {}", query.getId());
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(query.getId());
        return optionalInvoice
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + query.getId()));
    }
}