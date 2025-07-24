package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.MediaCommandService;
import org.example.polyinformatiquecoreapi.commands.CreateMediaCommand;

import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateMediaCommand;
import org.example.polyinformatiquecoreapi.dto.MediaDTO;
import org.springframework.stereotype.Service;

@Service
public class MediaCommandServiceImpl implements MediaCommandService {

    private final CommandGateway commandGateway;

    public MediaCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createMedia(MediaDTO dto) {
        commandGateway.sendAndWait(new CreateMediaCommand(dto.getId(), dto));
    }

    @Override
    public void updateMedia(String id, MediaDTO dto) {
        commandGateway.sendAndWait(new UpdateMediaCommand(id, dto));
    }

    @Override
    public void deleteMedia(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}