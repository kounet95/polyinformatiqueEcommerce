package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.polyinformatiquecoreapi.dto.EventDTO;
import org.example.queryblog.query.GetAllEventQuery;
import org.example.queryblog.query.GetEventByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<EventDTO>> getAllEvents() {
        return queryGateway.query(new GetAllEventQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(EventDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<EventDTO> getEventById(@PathVariable String id) {
        return queryGateway.query(new GetEventByIdQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(EventDTO.class));
    }
}
