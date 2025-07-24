package org.example.commandeblog.web;

import jakarta.validation.Valid;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.commandeblog.service.MediaCommandService;
import org.example.polyinformatiquecoreapi.dto.MediaDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/media/command")

public class MediaController {

    private final MediaCommandService mediaCommandService;
    private final EventStore eventStore;

    public MediaController(MediaCommandService mediaCommandService, EventStore eventStore) {
        this.mediaCommandService = mediaCommandService;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid MediaDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        mediaCommandService.createMedia(dto);
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
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        mediaCommandService.deleteMedia(id);
        return ResponseEntity.ok().build();
    }
}
