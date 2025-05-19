package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.TagDTO;

public class CreateTagCommand extends BaseCommand<String> {
    private final TagDTO payload;

    public CreateTagCommand(String id, TagDTO payload) {
        super(id);
        this.payload = payload;
    }

    public TagDTO getPayload() {
        return payload;
    }
}

