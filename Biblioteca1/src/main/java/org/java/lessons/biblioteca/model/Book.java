package org.java.lessons.biblioteca.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="books")

public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="title must be not null")
	@NotEmpty(message="title must be not empty")
	private String title;
	
	@NotNull(message="author must be not null")
	@NotEmpty(message="author must be not empty")
	private String author;
	
	@Size(min = 13, max = 13, message = "isbn must be 13 characters")
	@NotNull(message="isbn must be not null")	
	@Column(name="isbn_code",  nullable = false , unique = true, columnDefinition = "CHAR(13)" )
	private String isbn;
	
	private String immagine;
	
	@NotNull(message="available copies must be not null")
	@PositiveOrZero
	private Integer availableCopies;
	
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Borrowing> borrowings;
	
	@ManyToMany()
	private List<Category> categories;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImmagine() {
		return immagine;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public List<Borrowing> getBorrowings() {
		return borrowings;
	}
	public void setBorrowings(List<Borrowing> borrowings) {
		this.borrowings = borrowings;
	}
	
	public Integer getAvailableCopies() {
		return availableCopies;
	}
	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	
}
