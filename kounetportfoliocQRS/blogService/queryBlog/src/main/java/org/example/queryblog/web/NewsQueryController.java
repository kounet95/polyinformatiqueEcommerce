package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.polyinformatiquecoreapi.dto.NewsDTO;
import org.example.queryblog.query.GetAllNewsQuery;
import org.example.queryblog.query.GetNewsByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<NewsDTO>> getAllNews() {
        return queryGateway.query(new GetAllNewsQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(NewsDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<NewsDTO> getNewsById(@PathVariable String id) {
        return queryGateway.query(new GetNewsByIdQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(NewsDTO.class));
    }
}
