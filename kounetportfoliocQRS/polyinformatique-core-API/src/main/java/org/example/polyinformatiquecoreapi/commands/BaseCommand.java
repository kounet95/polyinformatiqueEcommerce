package org.example.polyinformatiquecoreapi.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.polyinformatiquecoreapi.dto.*;

import java.util.Objects;

// =============================== //
// ====== Base Command Class ===== //
// =============================== //
public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier
    protected final T id;

    public BaseCommand(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
