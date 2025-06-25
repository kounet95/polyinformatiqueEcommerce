package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final QueryGateway queryGateway;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllCategories() {
      try {
        List<Category> categories = queryGateway.query(new GetAllCategoriesQuery(),
          ResponseTypes.multipleInstancesOf(Category.class)).join();
        return ResponseEntity.ok(categories);
      } catch (Exception e) {
        log.error("Erreur lors de la récupération des catégories", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
    }
    @GetMapping("/{id}")
    public CompletableFuture<Category> getCategoryById(@PathVariable String id) {
        return queryGateway.query(new GetCategoryByIdQuery(id),
                ResponseTypes.instanceOf(Category.class));
    }


}
