package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.CommentDTO;

public interface CommentCommandService {
    void createComment(CommentDTO dto);
    void updateComment(String id, CommentDTO dtoOldest, CommentDTO dtoNe);
    void deleteComment(String id);
}