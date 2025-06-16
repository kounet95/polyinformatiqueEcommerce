package org.example.ecpolyquery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Category {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Subcategory> subcategories;
}

