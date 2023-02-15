package org.java.lessons.biblioteca.controller;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository repository;
	
	
	@GetMapping()		// GET /books oppure GET /books?title=xxx
	public String index(
			@RequestParam(name="keyword", required = false) String keyword,
			Model model) {		
		List<Book> res;
		
		if (keyword!=null && !keyword.isEmpty())
			res = repository.findByTitleLike("%"+keyword+"%");  //tutti i libri il cui titolo contiene la parola chiave
		else
			res = repository.findAll();	//tutti i libri
		model.addAttribute("elencoLibri", res);
		return "books/index";
	}
	
	@GetMapping("/{id}")		//gestirà le richieste GET di tipo /books/x  (x è l'id del libro)
	public String detail(@PathVariable("id") Integer id, Model model) {
		Book book=repository.getReferenceById(id);
		model.addAttribute("book", book);
		return "books/detail";
	}
	
}
