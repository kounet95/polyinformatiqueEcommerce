package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;

import java.io.Serializable;

public class DomainDeletedEvent extends BaseEvent<String> implements Serializable {


    public DomainDeletedEvent(String id) {
        super(id);


    }


}
