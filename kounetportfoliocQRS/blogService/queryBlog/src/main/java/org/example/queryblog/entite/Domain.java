package org.example.queryblog.entite;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity(name = "blogdomain.comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Domain {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String tag_id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "domain")
    private List<Article> articles;
}
