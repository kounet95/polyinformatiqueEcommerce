package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.TagCommandService;
import org.example.polyinformatiquecoreapi.commands.CreateTagCommand;

import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateTagCommand;
import org.example.polyinformatiquecoreapi.dto.TagDTO;
import org.springframework.stereotype.Service;

@Service
public class TagCommandServiceImpl implements TagCommandService {

    private final CommandGateway commandGateway;

    public TagCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createTag(TagDTO dto) {
        commandGateway.sendAndWait(new CreateTagCommand(dto.getId(), dto));
    }

    @Override
    public void updateTag(String id, TagDTO dto) {
        commandGateway.sendAndWait(new UpdateTagCommand(id, dto));
    }

    @Override
    public void deleteTag(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}