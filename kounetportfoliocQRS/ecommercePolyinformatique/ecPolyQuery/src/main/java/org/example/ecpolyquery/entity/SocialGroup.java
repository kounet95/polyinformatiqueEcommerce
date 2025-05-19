package org.example.ecpolyquery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "eco_social_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SocialGroup {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String region;
    private String country;

    @OneToMany(mappedBy = "socialGroup")
    private List<Product> products;
}
