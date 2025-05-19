package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.NewsDTO;

public class UpdateNewsCommand extends BaseCommand<String> {
    private final NewsDTO newsDTO;

    public UpdateNewsCommand(
            String id, NewsDTO newsDTO

            ) {
        super(id);

        this.newsDTO = newsDTO;
    }

    public NewsDTO getNewsDTO() {
        return newsDTO;
    }
}

