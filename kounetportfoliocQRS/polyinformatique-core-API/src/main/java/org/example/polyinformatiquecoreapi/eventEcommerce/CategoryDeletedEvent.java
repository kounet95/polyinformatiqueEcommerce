package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;

/**
 * Event emitted when a category is deleted
 */
@Getter
public class CategoryDeletedEvent {
    private final String id;

    public CategoryDeletedEvent(String id) {
        this.id = id;
    }
}