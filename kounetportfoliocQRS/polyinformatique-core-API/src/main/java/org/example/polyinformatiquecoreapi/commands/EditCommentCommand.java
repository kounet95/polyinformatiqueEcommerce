package org.example.polyinformatiquecoreapi.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
@Getter
public class EditCommentCommand extends BaseCommand<String> {
    private final CommentDTO commentDTOOldest;
    private final CommentDTO commentDTONew;

    public EditCommentCommand(String id,  CommentDTO commentDTOOldest, CommentDTO commentDTONew) {
        super(id);
        this.commentDTOOldest = commentDTOOldest;
        this.commentDTONew = commentDTONew;
    }

}
