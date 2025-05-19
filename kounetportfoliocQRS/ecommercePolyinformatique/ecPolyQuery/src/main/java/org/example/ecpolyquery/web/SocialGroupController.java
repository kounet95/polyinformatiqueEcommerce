package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.SocialGroup;
import org.example.ecpolyquery.query.GetAllSocialGroupsQuery;
import org.example.ecpolyquery.query.GetSocialGroupByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/socialgroups")
@AllArgsConstructor
public class SocialGroupController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<SocialGroup>> getAllSocialGroups() {
        return queryGateway.query(new GetAllSocialGroupsQuery(), 
                ResponseTypes.multipleInstancesOf(SocialGroup.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<SocialGroup> getSocialGroupById(@PathVariable String id) {
        return queryGateway.query(new GetSocialGroupByIdQuery(id), 
                ResponseTypes.instanceOf(SocialGroup.class));
    }
}