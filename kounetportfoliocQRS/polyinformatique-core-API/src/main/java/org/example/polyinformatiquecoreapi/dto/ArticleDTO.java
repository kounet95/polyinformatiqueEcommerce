package org.example.polyinformatiquecoreapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


//===============================//
//========== ARTICLE ===========//
//===============================//
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ArticleDTO implements Serializable {

    private String id;
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
   @NotBlank(message = "urlMedia cannot be blank")
    private String urlMedia;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;
    @NotNull(message = "Date cannot be null")
    private LocalDate createdAt;
    @NotBlank(message = "Author ID cannot be blank")
    @Size(min = 1, max = 50, message = "Author ID must be between 1 and 50 characters")
    private String authorId;
    @NotBlank(message = "Domain ID cannot be blank")
    @Size(min = 1, max = 50, message = "Domain ID must be between 1 and 50 characters")
    private String domainId;
    @NotEmpty(message = "tagIds cannot be empty")
    private List<String> tagIds;
    @NotEmpty(message = "commentIds cannot be empty")
    private List<String> commentIds;

}
