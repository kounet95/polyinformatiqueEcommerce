package org.example.queryblog.repos;


import org.example.queryblog.entite.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,String> {

}
