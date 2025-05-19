package org.example.commandeblog.web;

import jakarta.validation.Valid;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.commandeblog.service.CommentCommandService;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment/command")
@CrossOrigin
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final EventStore eventStore;

    public CommentController(CommentCommandService commentCommandService, EventStore eventStore) {
        this.commentCommandService = commentCommandService;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid CommentDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        commentCommandService.createComment(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable String commentId) {
        commentCommandService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events/{id}")
    public List<Object> getEvents(@PathVariable String id) {
        return eventStore.readEvents(id)
                .asStream()
                .map(evt -> evt.getPayload())
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}