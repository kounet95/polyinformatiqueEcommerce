package org.example.commandeblog.web;

import jakarta.validation.Valid;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.commandeblog.service.DomainCommandService;
import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/domain/command")
@CrossOrigin
public class DomainController {

    private final DomainCommandService domainCommandService;
    private final EventStore eventStore;

    public DomainController(DomainCommandService domainCommandService, EventStore eventStore) {
        this.domainCommandService = domainCommandService;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid DomainDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        domainCommandService.createDomain(dto);
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
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        domainCommandService.deleteDomain(id);
        return ResponseEntity.ok().build();
    }
}