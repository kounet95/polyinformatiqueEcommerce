package org.example.queryblog.repos;

import org.example.queryblog.entite.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

}
