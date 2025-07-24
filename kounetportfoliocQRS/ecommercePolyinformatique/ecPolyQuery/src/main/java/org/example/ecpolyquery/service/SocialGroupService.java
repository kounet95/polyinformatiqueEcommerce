package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.SocialGroup;
import org.example.ecpolyquery.repos.SocialGroupRepository;
import org.example.ecpolyquery.query.GetAllSocialGroupsQuery;
import org.example.ecpolyquery.query.GetSocialGroupByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.SocialGroupCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SocialGroupDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SocialGroupUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SocialGroupService {

  private final SocialGroupRepository socialGroupRepository;

  @EventHandler
  public void on(SocialGroupCreatedEvent event) {
    log.debug("Handling SocialGroupCreatedEvent: {}", event.getId());
    SocialGroupDTO dto = event.getSocialGroupDTO();

    if (!socialGroupRepository.existsById(event.getId())) {
      SocialGroup socialGroup = SocialGroup.builder()
        .id(event.getId())
        .name(dto.getName())
        .region(dto.getRegion())
        .country(dto.getPays())
        .build();
      socialGroupRepository.save(socialGroup);
      log.info("SocialGroup created with ID: {}", event.getId());
    } else {
      log.warn("SocialGroup with ID {} already exists! Ignoring.", event.getId());
    }
  }

  @EventHandler
  public void on(SocialGroupDeletedEvent event) {
    log.debug("Handling SocialGroupDeletedEvent: {}", event.getId());
    socialGroupRepository.findById(event.getId()).ifPresent(socialGroup -> {
      socialGroupRepository.delete(socialGroup);
      log.info("SocialGroup deleted with ID: {}", event.getId());
    });
  }

  @EventHandler
  public void on(SocialGroupUpdatedEvent event) {
    log.debug("Handling SocialGroupUpdatedEvent: {}", event.getId());
    SocialGroupDTO dto = event.getSocialGroupDTO();
    socialGroupRepository.findById(event.getId()).ifPresent(socialGroup -> {
      socialGroup.setName(dto.getName());
      socialGroup.setRegion(dto.getRegion());
      socialGroup.setCountry(dto.getPays());
      socialGroupRepository.save(socialGroup);
      log.info("SocialGroup updated with ID: {}", event.getId());
    });
  }

  @QueryHandler
  public List<SocialGroup> handle(GetAllSocialGroupsQuery query) {
    return socialGroupRepository.findAll();
  }

  @QueryHandler
  public SocialGroup handle(GetSocialGroupByIdQuery query) {
    return socialGroupRepository.findById(query.getId()).orElse(null);
  }
}
