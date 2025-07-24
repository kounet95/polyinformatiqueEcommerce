package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;

import java.io.Serializable;

public class DomainCreateEvent extends BaseEvent<String> implements Serializable  {
    private final DomainDTO domainDTO;

    public DomainCreateEvent(String id,  DomainDTO domainDTO) {
        super(id);
        this.domainDTO = domainDTO;

    }

    public DomainDTO getDomainDTO() {
        return domainDTO;
    }
}
