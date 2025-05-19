package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.ArticleCommandService;
import org.example.polyinformatiquecoreapi.commands.CreatePostCommand;
import org.example.polyinformatiquecoreapi.commands.UpdatePostCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.springframework.stereotype.Service;

@Service
public class ArticleCommandServiceImpl implements ArticleCommandService {

    private final CommandGateway commandGateway;

    public ArticleCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createArticle(ArticleDTO dto) {
        commandGateway.sendAndWait(new CreatePostCommand(dto.getId(), dto));
    }

    @Override
    public void updateArticle(String id, ArticleDTO dto) {
        commandGateway.sendAndWait(new UpdatePostCommand(id, dto));
    }

    @Override
    public void deleteArticle(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}