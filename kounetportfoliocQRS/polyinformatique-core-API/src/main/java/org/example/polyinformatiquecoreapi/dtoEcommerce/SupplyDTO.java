package org.example.polyinformatiquecoreapi.dtoEcommerce;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class SupplyDTO {

  private String id;
  private String name;
  private LocalDateTime createdAt;
 private List<String> stocksIds;
}
