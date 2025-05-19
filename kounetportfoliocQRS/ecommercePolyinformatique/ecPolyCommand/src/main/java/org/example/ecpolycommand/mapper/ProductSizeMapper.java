package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.ecpolycommand.aggregate.ProductSizeAggregate;
import org.springframework.stereotype.Component;

@Component
public class ProductSizeMapper {

    public ProductSizeAggregate toAggregate(ProductSizeDTO dto) {
        ProductSizeAggregate agg = new ProductSizeAggregate();
        agg.setProductSizeId(dto.getId());
        agg.setProductId(dto.getProductId());
        agg.setSize(dto.getSize());
        return agg;
    }

    public ProductSizeDTO toDTO(ProductSizeAggregate agg) {
        return new ProductSizeDTO(
            agg.getProductSizeId(),
            agg.getProductId(),
            agg.getSize()
        );
    }
}