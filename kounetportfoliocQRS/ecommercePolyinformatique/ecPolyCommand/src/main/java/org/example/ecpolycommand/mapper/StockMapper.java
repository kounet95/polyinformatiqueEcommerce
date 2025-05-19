package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.ecpolycommand.aggregate.StockAggregate;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public StockAggregate toAggregate(StockDTO dto) {
        StockAggregate agg = new StockAggregate();
        agg.setProductId(dto.getId());
        agg.setQuantity((int) dto.getQuantity());
        // Les autres champs du DTO ne sont pas présents dans l'aggregate
        return agg;
    }

    public StockDTO toDTO(StockAggregate agg) {
        return new StockDTO(
            agg.getProductId(),
            null, // productSizeId non présent dans l'aggregate
            null, // supplierId non présent dans l'aggregate
            0,    // purchasePrice non présent dans l'aggregate
            0,    // promoPrice non présent dans l'aggregate
            0,    // salePrice non présent dans l'aggregate
            0,    // stockAvailable non présent dans l'aggregate
            agg.getQuantity()
        );
    }
}