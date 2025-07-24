package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}