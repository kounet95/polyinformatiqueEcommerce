package org.example.polyinformatiquecoreapi.commands;

public class DeleteCommentCommand extends BaseCommand<String> {
    private final String commentId;

    public DeleteCommentCommand(String id, String commentId) {
        super(id);
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }
}
