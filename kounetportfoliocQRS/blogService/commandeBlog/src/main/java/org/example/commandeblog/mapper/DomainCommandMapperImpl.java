package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.DomainAggregate;
import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainCommandMapperImpl {

    public DomainAggregate toAggregate(DomainDTO dto) {
        if (dto == null) return null;
        DomainAggregate aggregate = new DomainAggregate();
        aggregate.setId(dto.getId());
        aggregate.setName(dto.getName());
        aggregate.setArticles(dto.getArticles());
        return aggregate;
    }

    public DomainDTO toDTO(DomainAggregate aggregate) {
        if (aggregate == null) return null;
        return new DomainDTO(
            aggregate.getId(),
            aggregate.getName(),
            aggregate.getArticles()
        );
    }
}