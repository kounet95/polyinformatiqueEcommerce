package org.example.ecpolyquery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "eco_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private boolean isActive;
    private String couleurs;
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    @ManyToOne
    @JoinColumn(name = "social_group_id")
    private SocialGroup socialGroup;
    private ProductSize sizes;
    @OneToMany(mappedBy = "productId")
    private List<OrderLine> orderLines;
    private String image;
}

