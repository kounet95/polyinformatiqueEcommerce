package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.example.polyinformatiquecoreapi.dto.ItemDTO;

public class AddCommentCommand extends BaseCommand<String> {
    private final CommentDTO commentDTO;


    public AddCommentCommand(String id, CommentDTO commentDTO) {
        super(id);
        this.commentDTO = commentDTO;

    }

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }
}
