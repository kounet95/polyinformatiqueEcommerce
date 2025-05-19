package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.NewsDTO;

import java.time.LocalDate;
import java.util.List;

public class CreateNewsCommand extends BaseCommand<String> {
   private final NewsDTO newsDTO;



    public CreateNewsCommand(String id, NewsDTO newsDTO) {
        super(id);

        this.newsDTO = newsDTO;
    }

    public NewsDTO getNewsDTO() {
        return newsDTO;
    }
}


