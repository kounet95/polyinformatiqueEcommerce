package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.example.polyinformatiquecoreapi.event.CommentAddedEvent;
import org.example.polyinformatiquecoreapi.event.CommentDeletedEvent;
import org.example.polyinformatiquecoreapi.event.CommentEditedEvent;
import org.example.polyinformatiquecoreapi.query.WatchCommentQuery;

import org.example.queryblog.entite.Comment;
import org.example.queryblog.mapper.CommentMapper;
import org.example.queryblog.query.GetAllCommentQuery;
import org.example.queryblog.query.GetCommentByIdQuery;
import org.example.queryblog.repos.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentQueryHandler {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<CommentDTO> on(GetAllCommentQuery query) {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public CommentDTO on(GetCommentByIdQuery query) {
        Optional<Comment> optionalComment = commentRepository.findById(query.getId());
        return optionalComment
                .map(commentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + query.getId()));
    }

    @QueryHandler
    public CommentDTO on(WatchCommentQuery query) {
        log.debug("Handling WatchCommentQuery: {}", query.getCommentId());
        Optional<Comment> optionalComment = commentRepository.findById(query.getCommentId());
        return optionalComment
                .map(commentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + query.getCommentId()));
    }

    @EventHandler
    public void on(CommentAddedEvent event) {
        log.debug("Handling CommentAddedEvent for subscription queries: {}", event.getId());
        // The event already contains the CommentDTO
        CommentDTO commentDTO = event.getCommentDTO();

        // Emit update to subscribers watching this comment
        queryUpdateEmitter.emit(WatchCommentQuery.class,
                query -> query.getCommentId().equals(event.getId()),
                commentDTO);
    }

    @EventHandler
    public void on(CommentEditedEvent event) {
        log.debug("Handling CommentEditedEvent for subscription queries: {}", event.getId());

        // Find the comment and convert to DTO
        Optional<Comment> optionalComment = commentRepository.findById(event.getId());
        if (optionalComment.isPresent()) {
            CommentDTO commentDTO = commentMapper.toDTO(optionalComment.get());

            // Emit update to subscribers watching this comment
            queryUpdateEmitter.emit(WatchCommentQuery.class,
                    query -> query.getCommentId().equals(event.getId()),
                    commentDTO);
        }
    }

    @EventHandler
    public void on(CommentDeletedEvent event) {
        log.debug("Handling CommentDeletedEvent for subscription queries: {}", event.getId());

        // For deleted comments, we can emit a null or a special DTO indicating deletion
        // Here we'll create a new CommentDTO with minimal information
        CommentDTO deletedCommentDTO = new CommentDTO(
                event.getId(),
                "This comment has been deleted",
                java.time.LocalDate.now(),
                "",
                ""
        );

        // Emit update to subscribers watching this comment
        queryUpdateEmitter.emit(WatchCommentQuery.class,
                query -> query.getCommentId().equals(event.getId()),
                deletedCommentDTO);
    }
}
