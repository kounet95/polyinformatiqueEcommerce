package org.example.polyinformatiquecoreapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//===============================//
//=========== EVENT ============//
//===============================//
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EventDTO implements Serializable {
    private String id;
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @NotNull(message = "Begin cannot be null")
    private LocalDateTime   begin;
    @NotNull(message = "End cannot be null")
    private LocalDateTime  end;
    @NotBlank(message = "Content cannot be blank")
    private String content;
    @NotBlank(message = "urlMedia cannot be blank")
    @Size(min = 5, max = 1000, message = "urlMedia must be between 5 and 1000 characters")
    private String urlMedia;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;
    @NotNull(message = "Date cannot be null")
    private LocalDate createdAt;
    @NotBlank(message = "Author ID cannot be blank")
    private String authorId;
    @NotBlank(message = "Domain ID cannot be blank")
    private String domainId;
    @NotEmpty(message = "tagIds cannot be empty")
    private List<String> tagIds;
    @NotEmpty(message = "commentIds cannot be empty")
    private List<String> commentIds;
}

