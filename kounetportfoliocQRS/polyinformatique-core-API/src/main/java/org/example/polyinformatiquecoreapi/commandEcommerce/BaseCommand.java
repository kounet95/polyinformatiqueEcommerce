package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
  @TargetAggregateIdentifier
  protected final T id;

  protected BaseCommand(T id) {
    this.id = id;
  }

  public T getId() {
    return id;
  }
}
