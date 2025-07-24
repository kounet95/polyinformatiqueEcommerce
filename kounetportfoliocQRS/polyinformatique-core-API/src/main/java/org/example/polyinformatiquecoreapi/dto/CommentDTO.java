package org.example.polyinformatiquecoreapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

//===============================//
//========= COMMENT ============//
//===============================//

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CommentDTO implements Serializable {

    private String id;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
     private String contenu;
    @NotNull(message = "Date cannot be null")
    private LocalDate createdAt;
    @NotBlank(message = "Author ID cannot be blank")
    @Size(min = 1, max = 50, message = "Author ID must be between 1 and 50 characters")
    private String authorId;
    @NotBlank(message = "Item ID cannot be blank")
    private String itemId;

    public String getAuthorId() {
        return authorId;
    }

    public String getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getItemId() {
        return itemId;
    }
}

