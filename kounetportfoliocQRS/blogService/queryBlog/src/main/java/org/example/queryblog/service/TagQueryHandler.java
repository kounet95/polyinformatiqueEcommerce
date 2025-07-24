package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.TagDTO;
import org.example.polyinformatiquecoreapi.event.TagCreatedEvent;

import org.example.queryblog.entite.Tag;
import org.example.queryblog.mapper.TagMapper;
import org.example.queryblog.query.GetAllTagQuery;
import org.example.queryblog.query.GetTagByIdQuery;
import org.example.queryblog.repos.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TagQueryHandler {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<TagDTO> on(GetAllTagQuery query) {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public TagDTO on(GetTagByIdQuery query) {
        Optional<Tag> optionalTag = tagRepository.findById(query.getId());
        return optionalTag
                .map(tagMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + query.getId()));
    }

    @EventHandler
    public void on(TagCreatedEvent event) {
        log.debug("Handling TagCreatedEvent for subscription queries: {}", event.getId());
        // The event already contains the TagDTO
        TagDTO tagDTO = event.getPayload();

        // If there were a WatchTagQuery, we would emit updates here
        // queryUpdateEmitter.emit(WatchTagQuery.class,
        //        query -> query.getTagId().equals(event.getId()),
        //        tagDTO);
    }
}
