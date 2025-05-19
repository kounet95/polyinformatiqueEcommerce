package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.example.ecpolycommand.aggregate.SocialGroupAggregate;
import org.springframework.stereotype.Component;

@Component
public class SocialGroupMapper {

    public SocialGroupAggregate toAggregate(SocialGroupDTO dto) {
        SocialGroupAggregate agg = new SocialGroupAggregate();
        agg.setSocialGroupId(dto.getId());
        agg.setName(dto.getName());
        agg.setDescription(dto.getRegion()); // region du DTO → description de l'aggregate
        agg.setType(dto.getCountry());       // country du DTO → type de l'aggregate
        return agg;
    }

    public SocialGroupDTO toDTO(SocialGroupAggregate agg) {
        return new SocialGroupDTO(
            agg.getSocialGroupId(),
            agg.getName(),
            agg.getDescription(), // description de l'aggregate → region du DTO
            agg.getType()         // type de l'aggregate → country du DTO
        );
    }
}