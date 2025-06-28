package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Supplier {
  @Id
  private String id;

  private String fullname;

  private String email;
  private String personToContact;


}
