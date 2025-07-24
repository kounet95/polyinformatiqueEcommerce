package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;

public class UpdateCategoryCommand extends BaseCommand<String> {
    private final CategoryDTO categoryDTO;

    public UpdateCategoryCommand(String id, CategoryDTO categoryDTO) {
        super(id);
        this.categoryDTO = categoryDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }
}