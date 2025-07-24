package org.example.polyinformatiquecoreapi.dtoEcommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class CreateOrderRequest {
  @JsonProperty("orderDTO")
  private OrderDTO orderDTO;

  @JsonProperty("custom")
  private boolean custom;
}


