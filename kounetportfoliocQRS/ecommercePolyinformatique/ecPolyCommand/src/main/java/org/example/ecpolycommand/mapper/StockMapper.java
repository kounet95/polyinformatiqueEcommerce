package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.ecpolycommand.aggregate.StockAggregate;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

  public StockAggregate toAggregate(StockDTO dto) {
    StockAggregate agg = new StockAggregate();
    agg.setId(dto.getId());

    agg.setProductSizeId(dto.getProductSizeId());
    agg.setSupplierId(dto.getSupplierId());
    agg.setPurchasePrice(dto.getPurchasePrice());
    agg.setPromoPrice(dto.getPromoPrice());
    agg.setQuantity(dto.getQuantity());
    agg.setSupplierId(dto.getSupplierId());

    return agg;
  }

  public StockDTO toDTO(StockAggregate agg) {
    return new StockDTO(
      agg.getId(),
      agg.getProductSizeId(),
      agg.getSupplierId(),
      agg.getPurchasePrice(),
      agg.getPromoPrice(),
      agg.getQuantity(),
      agg.getSupplierId()

    );
  }
}
