package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetAllSubcategoriesQuery;
import org.example.ecpolyquery.query.GetSubcategoryByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subcategories")
@AllArgsConstructor
public class SubcategoryController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<SubcategoryDTO>> getAllSubcategories() {
    return queryGateway.query(new GetAllSubcategoriesQuery(),
        ResponseTypes.multipleInstancesOf(Subcategory.class))
      .thenApply(subs -> subs.stream().map(SubcategoryController::toDto).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public CompletableFuture<SubcategoryDTO> getSubcategoryById(@PathVariable String id) {
    return queryGateway.query(new GetSubcategoryByIdQuery(id),
        ResponseTypes.instanceOf(Subcategory.class))
      .thenApply(SubcategoryController::toDto);
  }

  private static SubcategoryDTO toDto(Subcategory entity) {
    SubcategoryDTO dto = new SubcategoryDTO();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
    return dto;
  }
}
