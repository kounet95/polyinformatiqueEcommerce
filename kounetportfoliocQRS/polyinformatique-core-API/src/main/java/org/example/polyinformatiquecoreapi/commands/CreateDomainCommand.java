package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;

public class CreateDomainCommand extends BaseCommand<String> {
    private final DomainDTO payload;

    public CreateDomainCommand(String id, DomainDTO payload) {
        super(id);
        this.payload = payload;
    }

    public DomainDTO getPayload() {
        return payload;
    }
}

