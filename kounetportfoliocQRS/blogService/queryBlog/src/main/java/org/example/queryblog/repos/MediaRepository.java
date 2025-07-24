package org.example.queryblog.repos;


import org.example.queryblog.entite.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, String> {

}
