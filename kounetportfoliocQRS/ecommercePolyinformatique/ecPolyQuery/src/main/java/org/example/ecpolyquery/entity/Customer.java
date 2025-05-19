package org.example.ecpolyquery.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    // Champs e-commerce
    private String firstname;
    private String lastname;
    private String shippingAddress;
    private String billingAddress;
    private String email;
    private String phone;
    // Relations e-commerce
    @OneToMany(mappedBy = "customer")
    private List<Orderecommerce> orders;
}