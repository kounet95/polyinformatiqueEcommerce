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
        agg.setRegion(dto.getRegion());
        agg.setCountry(dto.getCountry());
        return agg;
    }

    public SocialGroupDTO toDTO(SocialGroupAggregate agg) {
        return new SocialGroupDTO(
            agg.getSocialGroupId(),
            agg.getName(),
            agg.getRegion(),
            agg.getCountry()
        );
    }
}
