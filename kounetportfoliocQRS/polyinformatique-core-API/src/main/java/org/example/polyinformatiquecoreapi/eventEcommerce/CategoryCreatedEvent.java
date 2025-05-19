package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class CategoryCreatedEvent extends BaseEvent<String> implements Serializable {
    private CategoryDTO categoryDTO;

    public CategoryCreatedEvent(String id, CategoryDTO categoryDTO) {
        super(id);
        this.categoryDTO = categoryDTO;
    }
    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }
}
