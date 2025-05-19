package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.DomainCommandService;
import org.example.polyinformatiquecoreapi.commands.CreateDomainCommand;

import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateDomainCommand;
import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.springframework.stereotype.Service;

@Service
public class DomainCommandServiceImpl implements DomainCommandService {

    private final CommandGateway commandGateway;

    public DomainCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createDomain(DomainDTO dto) {
        commandGateway.sendAndWait(new CreateDomainCommand(dto.getId(), dto));
    }

    @Override
    public void updateDomain(String id, DomainDTO dto) {
        commandGateway.sendAndWait(new UpdateDomainCommand(id, dto));
    }

    @Override
    public void deleteDomain(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}