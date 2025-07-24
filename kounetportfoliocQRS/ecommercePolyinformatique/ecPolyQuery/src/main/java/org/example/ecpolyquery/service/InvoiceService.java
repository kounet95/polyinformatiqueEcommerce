package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Invoice;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.repos.InvoiceRepository;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoiceGeneratedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoicePaidEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderecommerceRepository orderecommerceRepository;

    @EventHandler
    public void on(InvoiceGeneratedEvent event) {
        log.debug("Handling InvoiceGeneratedEvent: {}", event.getId());
        InvoiceDTO invoiceDTO = event.getInvoiceDTO();

        Orderecommerce order = orderecommerceRepository.findById(invoiceDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + invoiceDTO.getOrderId()));

        Invoice invoice = Invoice.builder()
          .id(event.getId())
          .amount(invoiceDTO.getAmount())
          .restPayment(invoiceDTO.getRestMonthlyPayment())
          .paymentStatus(invoiceDTO.getPaymentStatus())
          .paymentMethod(invoiceDTO.getMethodePayment())
          .orderecommerce(order)
          .build();
        invoiceRepository.save(invoice);
        log.info("Invoice generated with ID: {}", invoice.getId());
    }

    @EventHandler
    public void on(InvoicePaidEvent event) {
        log.debug("Handling InvoicePaidEvent: {}", event.getId());

        Invoice invoice = invoiceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + event.getId()));

        invoice.setPaymentStatus("PAID");
        invoiceRepository.save(invoice);
        log.info("Invoice marked as paid with ID: {}", invoice.getId());
    }
}
