package org.java.lessons.biblioteca.repository;

import org.java.lessons.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
