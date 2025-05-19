package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderCancelledEvent extends BaseEvent<String> implements Serializable {
    private final String reason;
    private final LocalDateTime cancelledAt;

    public OrderCancelledEvent(String id, String reason) {
        super(id);
        this.reason = reason;
        this.cancelledAt = LocalDateTime.now();
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}