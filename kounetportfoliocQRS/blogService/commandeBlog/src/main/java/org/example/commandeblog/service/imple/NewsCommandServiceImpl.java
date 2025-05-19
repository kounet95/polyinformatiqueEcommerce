package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.mapper.NewsMapper;
import org.example.commandeblog.service.NewsCommandService;
import org.example.polyinformatiquecoreapi.dto.NewsDTO;

import org.example.polyinformatiquecoreapi.commands.CreateNewsCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateNewsCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.springframework.stereotype.Service;

@Service
public class NewsCommandServiceImpl implements NewsCommandService {

    private final CommandGateway commandGateway;
    private final NewsMapper mapper;

    public NewsCommandServiceImpl(CommandGateway commandGateway, NewsMapper mapper) {
        this.commandGateway = commandGateway;
        this.mapper = mapper;
    }

    @Override
    public void createNews(NewsDTO dto) {
        var aggregate = mapper.toAggregate(dto);
        var command = new CreateNewsCommand(aggregate.getId(), dto);
        commandGateway.sendAndWait(command);
    }

    @Override
    public void updateNews(String id, NewsDTO dto) {
        var command = new UpdateNewsCommand(id, dto);
        commandGateway.sendAndWait(command);
    }

    @Override
    public void deleteNews(String id) {
        var command = new DeleteItemCommand(id);
        commandGateway.sendAndWait(command);
    }
}