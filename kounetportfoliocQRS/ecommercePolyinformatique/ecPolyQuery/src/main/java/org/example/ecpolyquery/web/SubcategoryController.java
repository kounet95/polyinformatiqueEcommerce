package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetAllSubcategoriesQuery;
import org.example.ecpolyquery.query.GetSubcategoryByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subcategories")
@AllArgsConstructor
public class SubcategoryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Subcategory>> getAllSubcategories() {
        return queryGateway.query(new GetAllSubcategoriesQuery(), 
                ResponseTypes.multipleInstancesOf(Subcategory.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Subcategory> getSubcategoryById(@PathVariable String id) {
        return queryGateway.query(new GetSubcategoryByIdQuery(id), 
                ResponseTypes.instanceOf(Subcategory.class));
    }
}