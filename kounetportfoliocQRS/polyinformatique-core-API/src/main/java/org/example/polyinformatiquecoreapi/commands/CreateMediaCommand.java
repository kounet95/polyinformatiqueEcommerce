package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.MediaDTO;

public class CreateMediaCommand extends BaseCommand<String> {
    private final MediaDTO payload;

    public CreateMediaCommand(String id, MediaDTO payload) {
        super(id);
        this.payload = payload;
    }

    public MediaDTO getPayload() {
        return payload;
    }
}

