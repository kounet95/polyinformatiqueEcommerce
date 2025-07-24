package org.example.queryblog;

import org.example.queryblog.entite.Domain;

import org.example.queryblog.repos.DomainRepository;
import org.example.queryblog.repos.TagRepository;
import org.example.queryblog.repos.UtilisateurRepos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication

public class QueryBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryBlogApplication.class, args);
    }


}
