package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.ecpolycommand.aggregate.InvoiceAggregate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceAggregate toAggregate(InvoiceDTO dto) {
        InvoiceAggregate agg = new InvoiceAggregate();
        agg.setInvoiceId(dto.getInvoiceId());
        agg.setOrderId(dto.getOrderId());
        agg.setAmount(dto.getAmount());
        agg.setPaymentStatus(dto.getPaymentStatus());
        return agg;
    }

    public InvoiceDTO toDTO(InvoiceAggregate agg) {
        return new InvoiceDTO(
            agg.getInvoiceId(),
            agg.getOrderId(),
            agg.getAmount(),
            agg.getPaymentStatus()
        );
    }
}