package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.EventCommandService;
import org.example.polyinformatiquecoreapi.commands.CreateEventCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateEventCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.dto.EventDTO;
import org.springframework.stereotype.Service;

@Service
public class EventCommandServiceImpl implements EventCommandService {

    private final CommandGateway commandGateway;

    public EventCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createEvent(EventDTO dto) {
        commandGateway.sendAndWait(new CreateEventCommand(dto.getId(), dto));
    }

    @Override
    public void updateEvent(String id, EventDTO dto) {
        commandGateway.sendAndWait(new UpdateEventCommand(id, dto));
    }

    @Override
    public void deleteEvent(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}