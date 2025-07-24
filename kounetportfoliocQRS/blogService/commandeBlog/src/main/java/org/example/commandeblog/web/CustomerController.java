package org.example.commandeblog.web;

import jakarta.validation.Valid;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.commandeblog.service.AuthorCommandService;
import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/blog/author")

public class CustomerController {

    private final AuthorCommandService authorCommandService;
    private final EventStore eventStore;

    public CustomerController(AuthorCommandService authorCommandService, EventStore eventStore) {
        this.authorCommandService = authorCommandService;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public void createAuthor(@RequestBody @Valid CustomerBlogDTO author) {
        if (author.getId() == null || author.getId().isEmpty()) {
            author.setId(UUID.randomUUID().toString());
        }
        authorCommandService.createAuthor(author);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @DeleteMapping("/delete/{authorId}")
    public void deleteAuthor(@PathVariable String authorId) {
        authorCommandService.deleteAuthor(authorId);
    }
}
