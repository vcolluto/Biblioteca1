package org.java.lessons.biblioteca.controller;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BorrowingRepository borrowingRepository;

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
	
	@PostMapping("/create")  	//gestirà le richieste di tipo POST di tipo /borrowings/create
	public String store(
		@Valid @ModelAttribute("borrowing") Borrowing formBorrowing, 
		BindingResult bindingResult,
		Model model){
		
		if (bindingResult.hasErrors())
			return "borrowings/create";
		
		Book book=formBorrowing.getBook();
		book.setAvailableCopies(book.getAvailableCopies()-1);
		borrowingRepository.save(formBorrowing);
		
		return "redirect:/borrowings"; //genera un altro get
		
	}
	
	@GetMapping() 
	public String index(Model model) {
		List<Borrowing> borrowingList=borrowingRepository.findAll();
		model.addAttribute("elencoPrestiti", borrowingList);
		
		return "borrowings/index";
	}
	
}
