package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.repos.CategoryRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        log.debug("Handling CategoryCreatedEvent: {}", event.getId());
        CategoryDTO categoryDTO = event.getCategoryDTO();

        Category category = Category.builder()
                .id(event.getId())
                .name(categoryDTO.getName())
                .subcategories(new ArrayList<>())
                .build();

        categoryRepository.save(category);
        log.info("Category created with ID: {}", category.getId());
    }

    @EventHandler
    public void on(CategoryDeletedEvent event) {
        log.debug("Handling CategoryDeletedEvent: {}", event.getId());

        categoryRepository.findById(event.getId())
                .ifPresent(category -> {
                    categoryRepository.delete(category);
                    log.info("Category deleted with ID: {}", event.getId());
                });
    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        log.debug("Handling CategoryUpdatedEvent: {}", event.getId());
        CategoryDTO categoryDTO = event.getCategoryDTO();

        categoryRepository.findById(event.getId())
                .ifPresent(category -> {
                    category.setName(categoryDTO.getName());
                    categoryRepository.save(category);
                    log.info("Category updated with ID: {}", category.getId());
                });
    }
}
