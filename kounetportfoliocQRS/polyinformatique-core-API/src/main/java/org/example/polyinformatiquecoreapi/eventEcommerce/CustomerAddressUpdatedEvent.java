package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder
public class CustomerAddressUpdatedEvent implements Serializable {
  UUID customerId;
  UUID addressId;
}
