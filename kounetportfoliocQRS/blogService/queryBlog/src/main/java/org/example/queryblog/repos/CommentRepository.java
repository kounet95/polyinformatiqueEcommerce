package org.example.queryblog.repos;


import org.example.queryblog.entite.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
