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
        agg.setSubcategoryId(dto.getSubcategoryId());
        agg.setSocialGroupId(dto.getSocialGroupId());
        agg.setModels(dto.getModels());

        return agg;
    }

    public ProductDTO toDTO(ProductAggregate agg) {
        return new ProductDTO(
            agg.getProductId(),
            agg.getName(),
            agg.getDescription(),

            null,
            null,
            agg.getSubcategoryId(),
            agg.getSocialGroupId(),
            agg.getModels(),
            null
        );
    }
}
