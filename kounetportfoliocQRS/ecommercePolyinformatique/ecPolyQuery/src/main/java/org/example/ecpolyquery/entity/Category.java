package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "eco_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Category {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
  @Version
  private Long version;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Subcategory> subcategories;
}

