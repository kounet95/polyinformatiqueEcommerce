package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Category>> getAllCategories() {
        return queryGateway.query(new GetAllCategoriesQuery(), 
                ResponseTypes.multipleInstancesOf(Category.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Category> getCategoryById(@PathVariable String id) {
        return queryGateway.query(new GetCategoryByIdQuery(id), 
                ResponseTypes.instanceOf(Category.class));
    }
}