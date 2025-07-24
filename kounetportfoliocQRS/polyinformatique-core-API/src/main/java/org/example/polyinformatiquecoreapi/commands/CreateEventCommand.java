package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.example.polyinformatiquecoreapi.dto.EventDTO;


public class CreateEventCommand extends BaseCommand<String> {

    private final EventDTO eventDTO;
    public CreateEventCommand(String id, EventDTO eventDTO) {
        super(id);

        this.eventDTO = eventDTO;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }
}
