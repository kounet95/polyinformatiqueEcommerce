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
  private Long id;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  @OneToMany(mappedBy = "billingAddress", cascade = CascadeType.ALL)
  private List<Customer> customers;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Stock store;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Supplier supplier;
  @OneToMany(mappedBy = "addressId",cascade = CascadeType.ALL)
  private Shipping shipping;

}
