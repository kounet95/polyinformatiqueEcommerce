package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.NewsDTO;
import org.example.polyinformatiquecoreapi.event.NewsCreatedEvent;
import org.example.polyinformatiquecoreapi.event.NewsUpdatedEvent;
import org.example.queryblog.entite.News;
import org.example.queryblog.mapper.NewsMapper;
import org.example.queryblog.query.GetAllNewsQuery;
import org.example.queryblog.query.GetNewsByIdQuery;
import org.example.queryblog.repos.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class NewsQueryHandler {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<NewsDTO> on(GetAllNewsQuery query) {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public NewsDTO on(GetNewsByIdQuery query) {
        Optional<News> optionalNews = newsRepository.findById(query.getId());
        return optionalNews
                .map(newsMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + query.getId()));
    }

    @EventHandler
    public void on(NewsCreatedEvent event) {
        log.debug("Handling NewsCreatedEvent for subscription queries: {}", event.getId());
        // The event already contains the NewsDTO
        NewsDTO newsDTO = event.getNewsDTO();

        // If there were a WatchNewsQuery, we would emit updates here
        // queryUpdateEmitter.emit(WatchNewsQuery.class,
        //        query -> query.getNewsId().equals(event.getId()),
        //        newsDTO);
    }

    @EventHandler
    public void on(NewsUpdatedEvent event) {
        log.debug("Handling NewsUpdatedEvent for subscription queries: {}", event.getId());

        // Find the news and convert to DTO
        Optional<News> optionalNews = newsRepository.findById(event.getId());
        if (optionalNews.isPresent()) {
            NewsDTO newsDTO = newsMapper.toDTO(optionalNews.get());

            // If there were a WatchNewsQuery, we would emit updates here
            // queryUpdateEmitter.emit(WatchNewsQuery.class,
            //        query -> query.getNewsId().equals(event.getId()),
            //        newsDTO);
        }
    }
}
