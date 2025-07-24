package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;

/**
 * Event emitted when a customer is deleted
 */
@Getter
public class CustomerDeletedEvent {
    private final String id;

    public CustomerDeletedEvent(String id) {
        this.id = id;
    }
}