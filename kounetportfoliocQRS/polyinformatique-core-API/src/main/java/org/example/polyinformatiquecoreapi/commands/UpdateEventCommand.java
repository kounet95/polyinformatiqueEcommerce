package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.EventDTO;

import java.time.LocalDateTime;

public class UpdateEventCommand extends BaseCommand<String> {
    private final EventDTO event;
    public UpdateEventCommand(
            String id, EventDTO event

            ) {
        super(id);


        this.event = event;
    }

    public EventDTO getEvent() {
        return event;
    }
}
