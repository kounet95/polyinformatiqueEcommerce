package org.example.queryblog.entite;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "blogarticle")
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@DiscriminatorValue("Article")
public class Article extends Item{

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;
}
