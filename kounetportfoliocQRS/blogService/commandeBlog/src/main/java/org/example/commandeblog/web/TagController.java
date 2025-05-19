package org.example.commandeblog.web;

import jakarta.validation.Valid;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.commandeblog.service.TagCommandService;
import org.example.polyinformatiquecoreapi.dto.TagDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/tag/command")
@CrossOrigin
public class TagController {

    private final TagCommandService tagCommandService;
    private final EventStore eventStore;

    public TagController(TagCommandService tagCommandService, EventStore eventStore) {
        this.tagCommandService = tagCommandService;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid TagDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        tagCommandService.createTag(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events/{id}")
    public Stream<Object> getEvents(@PathVariable String id) {
        return eventStore.readEvents(id).asStream().map(e -> e.getPayload());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable String id) {
        tagCommandService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}