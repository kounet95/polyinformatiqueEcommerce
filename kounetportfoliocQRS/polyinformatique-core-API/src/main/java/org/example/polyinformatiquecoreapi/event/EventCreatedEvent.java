package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.EventDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventCreatedEvent extends BaseEvent<String> {
  private EventDTO eventDTO;

    public EventCreatedEvent(String id, EventDTO eventDTO) {
        super(id);

        this.eventDTO = eventDTO;
    }


    public EventDTO getEventDTO() {
        return eventDTO;
    }
}
