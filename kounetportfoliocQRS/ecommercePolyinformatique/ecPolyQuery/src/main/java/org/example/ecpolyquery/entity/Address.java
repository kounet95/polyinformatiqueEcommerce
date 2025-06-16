package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
  @Id
  private Long id;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Customer customer;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Stock store;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Supplier supplier;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Shipping shipping;

}
