package org.example.polyinformatiquecoreapi.event;


import java.io.Serializable;

public abstract class BaseEvent<T> implements Serializable {
    private final T id;

    protected BaseEvent(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}

