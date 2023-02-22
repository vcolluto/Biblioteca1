package org.java.lessons.biblioteca.controller;

import java.util.Optional;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
	
	@Autowired
	BookRepository bookRepository;

	@GetMapping("/create")		//gestirà le richieste GET di tipo /borrowings/create?bookId=xxx
	public String create(
		@RequestParam(name="bookId", required = true) Integer bookId,
		Model model) throws Exception {
		
		Borrowing borrowing=new Borrowing();	//non esiste ancora sul DB
		
		try {
			Book book = bookRepository.getReferenceById(bookId);
			borrowing.setBook(book);
		} catch (EntityNotFoundException e) {
			throw new Exception("Book not present. Id="+bookId);
		}
		
		
		/*
		 * OPPURE:
		 * 
		Optional<Book> res=bookRepository.findById(bookId);
		if (res.isPresent()) {
			borrowing.setBook(res.get());			
		} else
			throw new Exception("Book not present. Id="+bookId);			
*/	
		model.addAttribute("borrowing", borrowing);
		
		return "borrowings/create";
	}
	
}
