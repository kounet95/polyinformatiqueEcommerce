package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SubcategoryCreatedEvent extends BaseEvent<String> {
    private SubcategoryDTO subcategoryDTO;

    public SubcategoryCreatedEvent(String id , SubcategoryDTO subcategoryDTO) {

        super(id);
        this.subcategoryDTO = subcategoryDTO;
    }
    public SubcategoryDTO getSubcategoryDTO() {
        return subcategoryDTO;
    }
}
