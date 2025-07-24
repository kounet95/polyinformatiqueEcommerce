package org.example.queryblog.entite;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "blog_event")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@DiscriminatorValue("Event")
public class Event extends Item{
    private String location;
    @Column(name = "begin_date")
    private LocalDateTime  begin;

    @Column(name = "end_date")
    private LocalDateTime  end;

}
