package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.AuthorCommandService;

import org.example.polyinformatiquecoreapi.commands.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateAuthorCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;

import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthorCommandServiceImpl implements AuthorCommandService {

    private final CommandGateway commandGateway;

    public AuthorCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createAuthor(CustomerBlogDTO dto) {
        commandGateway.sendAndWait(new CreateCustomerCommand(dto.getId(), dto));
    }

    @Override
    public void updateAuthor(String id, CustomerBlogDTO dto) {
        commandGateway.sendAndWait(new UpdateAuthorCommand(id, dto));
    }

    @Override
    public void deleteAuthor(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}