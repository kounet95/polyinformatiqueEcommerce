package org.example.queryblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.polyinformatiquecoreapi.event.TagCreatedEvent;
import org.example.polyinformatiquecoreapi.event.ItemDeletedEvent;

import org.example.queryblog.entite.Item;
import org.example.queryblog.entite.Tag;
import org.example.queryblog.repos.IteamRepository;
import org.example.queryblog.repos.TagRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class tagService {

    private final TagRepository tagRepository;
    private final IteamRepository teamRepository;

    @EventHandler
    public void on(TagCreatedEvent event) {
        log.debug("Handling TagCreatedEvent: {}", event.getId());

        // On récupère l'item par leur ID
        List<String> tags=event.getPayload().getItemIds();
        List<Item> item = teamRepository.findAllById(tags);

        Tag tag = Tag.builder()
                .tag_items(item)
                .name(event.getPayload().getName())
                .build();
        tagRepository.save(tag);
    }

    @EventHandler
    public void on(ItemDeletedEvent event) {
        log.debug("Handling ItemDeletedEvent: {}", event.getId());

        tagRepository.deleteById(event.getId());
    }
}
