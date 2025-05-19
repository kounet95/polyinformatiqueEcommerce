package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.example.ecpolycommand.aggregate.SubcategoryAggregate;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    public SubcategoryAggregate toAggregate(SubcategoryDTO dto) {
        SubcategoryAggregate agg = new SubcategoryAggregate();
        agg.setSubcategoryId(dto.getId());
        agg.setName(dto.getName());
        agg.setCategoryId(dto.getCategoryId());
        return agg;
    }

    public SubcategoryDTO toDTO(SubcategoryAggregate agg) {
        return new SubcategoryDTO(
            agg.getSubcategoryId(),
            agg.getName(),
            agg.getCategoryId()
        );
    }
}