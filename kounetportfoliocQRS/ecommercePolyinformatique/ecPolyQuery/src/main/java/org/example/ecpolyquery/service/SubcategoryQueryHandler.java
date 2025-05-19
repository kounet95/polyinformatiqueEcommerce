package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetAllSubcategoriesQuery;
import org.example.ecpolyquery.query.GetSubcategoryByIdQuery;
import org.example.ecpolyquery.repos.SubcategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SubcategoryQueryHandler {

    private final SubcategoryRepository subcategoryRepository;

    @QueryHandler
    public List<Subcategory> on(GetAllSubcategoriesQuery query) {
        log.debug("Handling GetAllSubcategoriesQuery");
        return subcategoryRepository.findAll();
    }

    @QueryHandler
    public Subcategory on(GetSubcategoryByIdQuery query) {
        log.debug("Handling GetSubcategoryByIdQuery: {}", query.getId());
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(query.getId());
        return optionalSubcategory
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + query.getId()));
    }
}