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
public class Customer {
  @Id
  private String id;
  private String firstname;
  private String lastname;
  @ManyToOne(fetch = FetchType.LAZY)
  private Address billingAddress;
  private String email;
  private String phone;
}
