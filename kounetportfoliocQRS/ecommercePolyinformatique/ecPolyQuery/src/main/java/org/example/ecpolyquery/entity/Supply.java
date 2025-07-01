package org.example.ecpolyquery.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Supply {
  @Id
  private String id;
  private String name;
  private LocalDateTime created_at;
  @OneToMany(
    mappedBy = "supply",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER
  )
  @JsonIgnore
  private List<Stock> stockList;


}
