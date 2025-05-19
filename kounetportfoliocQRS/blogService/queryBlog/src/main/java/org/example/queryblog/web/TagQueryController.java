package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.polyinformatiquecoreapi.dto.TagDTO;
import org.example.queryblog.query.GetAllTagQuery;
import org.example.queryblog.query.GetTagByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<TagDTO>> getAllTags() {
        return queryGateway.query(new GetAllTagQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(TagDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<TagDTO> getTagById(@PathVariable String id) {
        return queryGateway.query(new GetTagByIdQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(TagDTO.class));
    }
}
