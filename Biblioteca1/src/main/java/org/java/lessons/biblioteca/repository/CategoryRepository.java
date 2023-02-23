package org.java.lessons.biblioteca.repository;

import org.java.lessons.biblioteca.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
