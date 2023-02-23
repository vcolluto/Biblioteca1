package org.java.lessons.biblioteca.controller;

import java.util.List;

import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
	
	
	@Autowired
	BorrowingService borrowingService;

	@GetMapping("/create")		//gestirà le richieste GET di tipo /borrowings/create?bookId=xxx
	public String create(
		@RequestParam(name="bookId", required = true) Integer bookId,
		Model model) throws Exception {
		
		Borrowing borrowing=borrowingService.Create(bookId);	//logica applicativa nel service
	
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
		
		borrowingService.save(formBorrowing);
		
		return "redirect:/borrowings"; //genera un altro get
		
	}
	
	@GetMapping() 
	public String index(Model model) {
		List<Borrowing> borrowingList=borrowingService.findAll();
		model.addAttribute("elencoPrestiti", borrowingList);
		
		return "borrowings/index";
	}
	
}
