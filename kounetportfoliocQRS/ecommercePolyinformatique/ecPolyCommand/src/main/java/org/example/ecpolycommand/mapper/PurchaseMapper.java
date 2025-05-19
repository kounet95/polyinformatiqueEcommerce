package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseDTO;
import org.example.ecpolycommand.aggregate.PurchaseAggregate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

    public PurchaseAggregate toAggregate(PurchaseDTO dto) {
        PurchaseAggregate agg = new PurchaseAggregate();
        agg.setPurchaseId(dto.getId());
        agg.setSupplierId(dto.getSupplierId());
        agg.setCreatedAt(dto.getCreatedAt());
        agg.setStatus(dto.getStatus());
        agg.setTotal(dto.getTotal());
        return agg;
    }

    public PurchaseDTO toDTO(PurchaseAggregate agg) {
        return new PurchaseDTO(
            agg.getPurchaseId(),
            agg.getSupplierId(),
            agg.getCreatedAt(),
            agg.getStatus(),
            agg.getTotal()
        );
    }
}