package org.example.commandeblog.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commandeblog.service.CommentCommandService;

import org.example.polyinformatiquecoreapi.commands.AddCommentCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteItemCommand;
import org.example.polyinformatiquecoreapi.commands.EditCommentCommand;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommandGateway commandGateway;

    public CommentCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createComment(CommentDTO dto) {
        commandGateway.sendAndWait(new AddCommentCommand(dto.getId(), dto));
    }


    @Override
    public void updateComment(String id, CommentDTO dtoOldest, CommentDTO dtoNew) {
        commandGateway.sendAndWait(new EditCommentCommand(id, dtoOldest, dtoNew));
    }


    @Override
    public void deleteComment(String id) {
        commandGateway.sendAndWait(new DeleteItemCommand(id));
    }
}