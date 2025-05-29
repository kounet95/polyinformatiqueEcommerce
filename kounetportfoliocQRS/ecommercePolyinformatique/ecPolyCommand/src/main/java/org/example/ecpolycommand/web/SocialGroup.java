package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSocialGroupCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/social-group/command")

public class SocialGroup {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public SocialGroup(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> createSocialGroup(@Valid @RequestBody SocialGroupDTO socialGroup) {
        String socialGroupId = UUID.randomUUID().toString();
        SocialGroupDTO socialGroupDTO = new SocialGroupDTO(
                socialGroupId,
                socialGroup.getName(),
                socialGroup.getRegion(),
                socialGroup.getCountry()
        );
        CreateSocialGroupCommand command = new CreateSocialGroupCommand(socialGroupId, socialGroupDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}
