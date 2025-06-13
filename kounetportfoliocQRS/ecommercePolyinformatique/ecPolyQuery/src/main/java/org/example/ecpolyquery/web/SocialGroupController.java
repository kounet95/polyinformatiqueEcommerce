package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.SocialGroup;
import org.example.ecpolyquery.query.GetAllSocialGroupsQuery;
import org.example.ecpolyquery.query.GetSocialGroupByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/socialgroups")
@AllArgsConstructor
public class SocialGroupController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<SocialGroupDTO>> getAllSocialGroups() {
    return queryGateway.query(new GetAllSocialGroupsQuery(),
        ResponseTypes.multipleInstancesOf(SocialGroup.class))
      .thenApply(groups -> groups.stream().map(SocialGroupController::toDto).collect(Collectors.toList()));
  }

  private static SocialGroupDTO toDto(SocialGroup entity) {
    SocialGroupDTO dto = new SocialGroupDTO();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setRegion(entity.getRegion());
    dto.setCountry(entity.getCountry());
    return dto;
  }
}
