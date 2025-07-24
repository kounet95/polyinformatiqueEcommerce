package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.example.ecpolyquery.repos.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryQueryHandler {

    private final CategoryRepository categoryRepository;

    @QueryHandler
    public List<Category> on(GetAllCategoriesQuery query) {
        log.debug("Handling GetAllCategoriesQuery");
        return categoryRepository.findAll();
    }

    @QueryHandler
    public Category on(GetCategoryByIdQuery query) {
        log.debug("Handling GetCategoryByIdQuery: {}", query.getId());
        Optional<Category> optionalCategory = categoryRepository.findById(query.getId());
        return optionalCategory
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + query.getId()));
    }
}