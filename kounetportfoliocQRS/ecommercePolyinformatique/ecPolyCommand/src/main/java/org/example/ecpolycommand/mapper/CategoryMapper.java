package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.example.ecpolycommand.aggregate.CategoryAggregate;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryAggregate toAggregate(CategoryDTO dto) {
        CategoryAggregate agg = new CategoryAggregate();
        agg.setCategoryId(dto.getId());
        agg.setName(dto.getName());
        return agg;
    }

    public CategoryDTO toDTO(CategoryAggregate agg) {
        return new CategoryDTO(
            agg.getCategoryId(),
            agg.getName()
        );
    }
}