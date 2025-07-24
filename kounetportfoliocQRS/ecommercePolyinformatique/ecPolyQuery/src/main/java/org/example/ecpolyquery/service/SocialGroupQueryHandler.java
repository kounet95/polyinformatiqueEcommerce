package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.SocialGroup;
import org.example.ecpolyquery.query.GetAllSocialGroupsQuery;
import org.example.ecpolyquery.query.GetSocialGroupByIdQuery;
import org.example.ecpolyquery.repos.SocialGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SocialGroupQueryHandler {

    private final SocialGroupRepository socialGroupRepository;

    @QueryHandler
    public List<SocialGroup> on(GetAllSocialGroupsQuery query) {
        log.debug("Handling GetAllSocialGroupsQuery");
        return socialGroupRepository.findAll();
    }

    @QueryHandler
    public SocialGroup on(GetSocialGroupByIdQuery query) {
        log.debug("Handling GetSocialGroupByIdQuery: {}", query.getId());
        Optional<SocialGroup> optionalSocialGroup = socialGroupRepository.findById(query.getId());
        return optionalSocialGroup
                .orElseThrow(() -> new RuntimeException("SocialGroup not found with id: " + query.getId()));
    }
}