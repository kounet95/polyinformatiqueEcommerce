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
public class Address {
  @Id
  private String id;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AddressLink> links;
}
