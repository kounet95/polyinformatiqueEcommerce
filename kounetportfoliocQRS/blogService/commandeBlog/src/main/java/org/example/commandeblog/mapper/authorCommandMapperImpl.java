package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.AuthorAggregate;
import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class authorCommandMapperImpl {

    public AuthorAggregate toAggregate(CustomerBlogDTO dto) {
        if (dto == null) return null;
        AuthorAggregate aggregate = new AuthorAggregate();
        aggregate.setAuthorId(dto.getId());
        aggregate.setUsername(dto.getUsername());
        aggregate.setLastname(dto.getLastname());
        aggregate.setEmail(dto.getEmail());
        aggregate.setName(dto.getName());
        aggregate.setPhone(dto.getPhone());
        aggregate.setAddress(dto.getAddress());
        aggregate.setItemIds(dto.getIteamIds());
        aggregate.setCommentIds(dto.getCommentIds());
        return aggregate;
    }

    public CustomerBlogDTO toDTO(AuthorAggregate aggregate) {
        if (aggregate == null) return null;
        return new CustomerBlogDTO(
            aggregate.getAuthorId(),
            aggregate.getUsername(),
            aggregate.getLastname(),
            aggregate.getEmail(),
            aggregate.getName(),
            aggregate.getPhone(),
            aggregate.getAddress(),
            aggregate.getItemIds(),
            aggregate.getCommentIds()
        );
    }
}