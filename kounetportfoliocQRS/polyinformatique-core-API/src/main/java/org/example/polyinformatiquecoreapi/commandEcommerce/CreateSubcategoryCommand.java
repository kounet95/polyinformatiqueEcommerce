package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;

public class CreateSubcategoryCommand extends BaseCommand<String> {
    private final SubcategoryDTO subcategoryDTO;

    public CreateSubcategoryCommand(String id, SubcategoryDTO subcategoryDTO) {
        super(id);
        this.subcategoryDTO = subcategoryDTO;
    }

    public SubcategoryDTO getSubcategoryDTO() {
        return subcategoryDTO;
    }
}