package org.example.polyinformatiquecoreapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//===============================//
//=========== ITEM =============//
//===============================//
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ItemDTO implements Serializable {
    private String id;
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
    @NotBlank(message = "urlMedia cannot be blank")
    @Size(min = 5, max = 100, message = "urlMedia must be between 5 and 100 characters")
    private String urlMedia;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;
    @NotNull(message = "Date cannot be null")
    private LocalDate createdAt;
    @NotBlank(message = "Author ID cannot be blank")
    private String authorId;
    @NotEmpty(message = "Domain ID cannot be empty")
    private List<String> mediaIds;
    @NotEmpty(message = "Domain ID cannot be empty")
    private List<String> commentIds;
    @NotEmpty(message = "Domain ID cannot be empty")
    private List<String> tagIds;

}

