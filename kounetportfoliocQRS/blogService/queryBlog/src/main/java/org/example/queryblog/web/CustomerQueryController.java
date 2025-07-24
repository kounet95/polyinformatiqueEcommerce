package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.example.queryblog.query.GetAllAuthorQuery;
import org.example.queryblog.query.GetAuthorByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/utilisateurs")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<CustomerBlogDTO>> getAllUtilisateurs() {
        return queryGateway.query(new GetAllAuthorQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(CustomerBlogDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<CustomerBlogDTO> getUtilisateurById(@PathVariable String id) {
        return queryGateway.query(new GetAuthorByIdQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(CustomerBlogDTO.class));
    }
}
