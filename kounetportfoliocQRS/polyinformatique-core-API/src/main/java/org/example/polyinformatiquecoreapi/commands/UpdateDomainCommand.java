package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;

public class UpdateDomainCommand extends BaseCommand<String> {

    private DomainDTO domainDTO;

    public UpdateDomainCommand(String id, DomainDTO domainDTO) {
        super(id);
        this.domainDTO = domainDTO;
    }
    public DomainDTO getDomainDTO() {
        return domainDTO;
    }
}
