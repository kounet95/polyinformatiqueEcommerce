package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;

public interface CategoryCommandService {
    void createCategory(CategoryDTO dto);
    void updateCategory(String id, CategoryDTO dto);
    void deleteCategory(String id);
}