package org.java.lessons.biblioteca.repository;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

	public List<Book> findByTitleLikeOrderByTitle(String keyword);
	public List<Book> findByAuthor(String author);
	public List<Book> findByIsbn(String isbn);
}
