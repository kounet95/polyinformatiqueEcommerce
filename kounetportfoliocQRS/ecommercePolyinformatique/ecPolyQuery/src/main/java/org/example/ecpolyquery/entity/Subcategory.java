package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "eco_subcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Subcategory {
    @Id

    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

