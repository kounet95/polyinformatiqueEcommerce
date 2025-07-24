package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.example.polyinformatiquecoreapi.query.WatchCommentQuery;
import org.example.queryblog.query.GetAllCommentQuery;
import org.example.queryblog.query.GetCommentByIdQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<CommentDTO>> getAllComments() {
        return queryGateway.query(new GetAllCommentQuery(),
                ResponseTypes.multipleInstancesOf(CommentDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<CommentDTO> getCommentById(@PathVariable String id) {
        return queryGateway.query(new GetCommentByIdQuery(id),
                ResponseTypes.instanceOf(CommentDTO.class));
    }

    @GetMapping(value = "/watch/{commentId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CommentDTO> watchComment(@PathVariable String commentId) {
        System.out.println(">>> Sending WatchCommentQuery for id: " + commentId);
        SubscriptionQueryResult<CommentDTO, CommentDTO> result = queryGateway.subscriptionQuery(
                new WatchCommentQuery(commentId),
                ResponseTypes.instanceOf(CommentDTO.class),
                ResponseTypes.instanceOf(CommentDTO.class)
        );
        return result.initialResult().concatWith(result.updates());
    }
}
