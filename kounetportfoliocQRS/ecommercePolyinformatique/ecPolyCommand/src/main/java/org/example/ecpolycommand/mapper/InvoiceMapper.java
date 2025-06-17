package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.ecpolycommand.aggregate.InvoiceAggregate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

  public InvoiceAggregate toAggregate(InvoiceDTO dto) {
    InvoiceAggregate agg = new InvoiceAggregate();
    agg.setInvoiceId(dto.getId());
    agg.setCustomerId(dto.getCustumerId());
    agg.setOrderId(dto.getOrderId());
    agg.setAmount(dto.getAmount());
    agg.setPaymentMethod(dto.getMethodePayment());
    agg.setPaymentStatus(dto.getPaymentStatus());
    agg.setRestMonthlyPayment(dto.getRestMonthlyPayment());
    agg.setSupplierId(dto.getSupplierId());
    return agg;
  }

  public InvoiceDTO toDTO(InvoiceAggregate agg) {
    return new InvoiceDTO(
      agg.getInvoiceId(),
      agg.getOrderId(),
      agg.getCustomerId(),
      agg.getAmount(),
      agg.getPaymentMethod(),
      agg.getRestMonthlyPayment(),
      agg.getPaymentStatus(),
      agg.getSupplierId()
    );
  }
}
