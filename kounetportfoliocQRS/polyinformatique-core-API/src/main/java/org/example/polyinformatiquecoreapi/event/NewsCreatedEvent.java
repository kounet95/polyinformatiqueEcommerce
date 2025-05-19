package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.NewsDTO;

import java.time.LocalDate;
import java.util.List;

public class NewsCreatedEvent extends BaseEvent<String> {
    private final NewsDTO newsDTO;



    public NewsCreatedEvent(
            String id, NewsDTO newsDTO

            ) {
        super(id);

        this.newsDTO = newsDTO;
    }

    public NewsDTO getNewsDTO() {
        return newsDTO;
    }
}

