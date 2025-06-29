package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter @AllArgsConstructor
@Builder
public class LinkAddressCommand {
  @TargetAggregateIdentifier
  private String addressId; // << c'est bien ça la clé de l'aggregate
  private String targetType; // ex: "CUSTOMER"
  private String targetId;   // ex: id du client
}
