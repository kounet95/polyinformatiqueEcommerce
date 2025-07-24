package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetAllSubcategoriesQuery;
import org.example.ecpolyquery.query.GetSubcategoryByIdQuery;
import org.example.ecpolyquery.repos.CategoryRepository;
import org.example.ecpolyquery.repos.SubcategoryRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.SubcategoryCreatedEvent;

import org.example.polyinformatiquecoreapi.eventEcommerce.SubcategoryDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SubcategoryUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SubcategoryService {

  private final SubcategoryRepository subcategoryRepository;
  private final CategoryRepository categoryRepository;

  @EventHandler
  public void on(SubcategoryCreatedEvent event) {
    log.debug("Handling SubcategoryCreatedEvent: {}", event.getId());
    SubcategoryDTO dto = event.getSubcategoryDTO();

    Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
    Subcategory.SubcategoryBuilder builder = Subcategory.builder()
      .id(event.getId())
      .name(dto.getName());

    if (categoryOpt.isPresent()) {
      builder.category(categoryOpt.get());
    } else {
      log.error("Category with ID {} not found for Subcategory creation! The Subcategory will be saved without category for now.", dto.getCategoryId());
      builder.category(null);
    }
    Subcategory subcategory = builder.build();
    subcategoryRepository.save(subcategory);
    log.info("Subcategory created with ID: {}", event.getId());
  }

  @EventHandler
  public void on(SubcategoryDeletedEvent event) {
    log.debug("Handling SubcategoryDeletedEvent: {}", event.getId());
    subcategoryRepository.findById(event.getId()).ifPresent(subcategory -> {
      subcategoryRepository.delete(subcategory);
      log.info("Subcategory deleted with ID: {}", event.getId());
    });
  }

  @EventHandler
  public void on(SubcategoryUpdatedEvent event) {
    log.debug("Handling SubcategoryUpdatedEvent: {}", event.getSubcategoryDTO().getId());
    SubcategoryDTO dto = event.getSubcategoryDTO();
    subcategoryRepository.findById(event.getSubcategoryDTO().getId()).ifPresent(subcategory -> {
      subcategory.setName(dto.getName());
      // Si la catégorie parente a ete changé, on la met à jour aussi
      if (!subcategory.getCategory().getId().equals(dto.getCategoryId())) {
        categoryRepository.findById(dto.getCategoryId()).ifPresent(subcategory::setCategory);
      }
      subcategoryRepository.save(subcategory);
      log.info("Subcategory updated with ID: {}", event.getSubcategoryDTO().getId());
    });
  }

  @QueryHandler
  public List<Subcategory> handle(GetAllSubcategoriesQuery query) {
    return subcategoryRepository.findAll();
  }

  @QueryHandler
  public Subcategory handle(GetSubcategoryByIdQuery query) {
    return subcategoryRepository.findById(query.getId()).orElse(null);
  }
}
