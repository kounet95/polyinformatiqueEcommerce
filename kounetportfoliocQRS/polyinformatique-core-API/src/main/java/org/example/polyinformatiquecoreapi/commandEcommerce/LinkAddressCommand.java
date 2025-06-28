package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter @AllArgsConstructor
@Builder
public class LinkAddressCommand {
  @TargetAggregateIdentifier
  private String targetId;
  private String targetType;
  private String addressId;
}
