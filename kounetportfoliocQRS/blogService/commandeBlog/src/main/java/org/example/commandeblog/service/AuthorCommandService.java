package org.example.commandeblog.service;


import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;

public interface AuthorCommandService {
    void createAuthor(CustomerBlogDTO dto);
    void updateAuthor(String id, CustomerBlogDTO dto);
    void deleteAuthor(String id);
}