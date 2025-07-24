package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.EventDTO;
import org.example.polyinformatiquecoreapi.event.EventCreatedEvent;
import org.example.polyinformatiquecoreapi.event.EventUpdatedEvent;
import org.example.polyinformatiquecoreapi.query.WatchEventQuery;
import org.example.queryblog.entite.Event;
import org.example.queryblog.mapper.EventMapper;
import org.example.queryblog.query.GetAllEventQuery;
import org.example.queryblog.query.GetEventByIdQuery;
import org.example.queryblog.repos.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventQueryHandler {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<EventDTO> on(GetAllEventQuery query) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public EventDTO on(GetEventByIdQuery query) {
        Optional<Event> optionalEvent = eventRepository.findById(query.getId());
        return optionalEvent
                .map(eventMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + query.getId()));
    }

    @QueryHandler
    public EventDTO on(WatchEventQuery query) {
        log.debug("Handling WatchEventQuery: {}", query.getId());
        Optional<Event> optionalEvent = eventRepository.findById(query.getId());
        return optionalEvent
                .map(eventMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + query.getId()));
    }

    @EventHandler
    public void on(EventCreatedEvent event) {
        log.debug("Handling EventCreatedEvent for subscription queries: {}", event.getId());
        // The event already contains the EventDTO
        EventDTO eventDTO = event.getEventDTO();

        // Emit update to subscribers watching this event
        queryUpdateEmitter.emit(WatchEventQuery.class,
                query -> query.getId().equals(event.getId()),
                eventDTO);
    }

    @EventHandler
    public void on(EventUpdatedEvent event) {
        log.debug("Handling EventUpdatedEvent for subscription queries: {}", event.getId());

        // Find the event and convert to DTO
        Optional<Event> optionalEvent = eventRepository.findById(event.getId());
        if (optionalEvent.isPresent()) {
            EventDTO eventDTO = eventMapper.toDTO(optionalEvent.get());

            // Emit update to subscribers watching this event
            queryUpdateEmitter.emit(WatchEventQuery.class,
                    query -> query.getId().equals(event.getId()),
                    eventDTO);
        }
    }
}
