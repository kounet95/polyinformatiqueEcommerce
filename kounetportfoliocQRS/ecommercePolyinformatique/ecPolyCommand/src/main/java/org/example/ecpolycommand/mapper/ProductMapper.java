package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.ecpolycommand.aggregate.ProductAggregate;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductAggregate toAggregate(ProductDTO dto) {
        ProductAggregate agg = new ProductAggregate();
        agg.setProductId(dto.getId());
        agg.setName(dto.getName());
        agg.setDescription(dto.getDescription());
        agg.setPrice(dto.getPrice());
        agg.setSubcategoryId(dto.getSubcategoryId());
        agg.setImageUrl(dto.getImageUrl());
        // Les champs socialGroupId, createdAt, closedAt, isActive ne sont pas présents dans l'aggregate
        return agg;
    }

    public ProductDTO toDTO(ProductAggregate agg) {
        return new ProductDTO(
            agg.getProductId(),
            agg.getName(),
            agg.getDescription(),
            agg.getPrice(),
            null, // createdAt non présent dans l'aggregate
            null, // closedAt non présent dans l'aggregate
            agg.getSubcategoryId(),
            null, // socialGroupId non présent dans l'aggregate
            agg.getImageUrl(),
            null  // isActive non présent dans l'aggregate
        );
    }
}