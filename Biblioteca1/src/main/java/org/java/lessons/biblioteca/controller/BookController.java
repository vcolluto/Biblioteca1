package org.java.lessons.biblioteca.controller;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Category;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping()		// GET /books oppure GET /books?title=xxx
	public String index(
			@RequestParam(name="keyword", required = false) String keyword,
			Model model) {		
		List<Book> res;
		
		if (keyword!=null && !keyword.isEmpty())
			res = bookRepository.findByTitleLikeOrderByTitle("%"+keyword+"%");  //tutti i libri il cui titolo contiene la parola chiave
		else
			res = bookRepository.findAll(Sort.by("title"));	//tutti i libri
		model.addAttribute("elencoLibri", res);
		return "books/index";
	}
	
	
	@GetMapping("/{id}")		//gestirà le richieste GET di tipo /books/x  (x è l'id del libro)
	public String detail(@PathVariable("id") Integer id, Model model) {
		Book book=bookRepository.getReferenceById(id);
		//System.out.println(book.getTitle());
		//System.out.println(book.getBorrowings().get(0).getBorrowingDate());
		model.addAttribute("book", book);
		return "books/detail";
	}
	
	@GetMapping("/create")		//gestirà le richieste GET di tipo /books/create
	public String create(Model model) {
		Book book=new Book();	//non esiste ancora sul DB
		//book.setTitle("undefined");
		List<Category> categoryList=categoryRepository.findAll();
		
		model.addAttribute("book", book);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("edit", false);
		return "books/edit";
	}
	
	@PostMapping("/create")  	//gestirà le richieste di tipo POST di tipo /books/create
	public String store(
		@Valid @ModelAttribute("book") Book formBook, 
		BindingResult bindingResult,
		Model model){
		
		
		if (bookRepository.findByIsbn(formBook.getIsbn()).size()>0)
			bindingResult.addError(new FieldError("book", "isbn", "duplicate isbn"));
		
		if (bindingResult.hasErrors())
			return "books/edit";
		
		
			
		bookRepository.save(formBook);
		
		return "redirect:/books"; //genera un altro get
		
	}
	
	@GetMapping("/edit/{id}")		//richieste GET del tipo /books/edit/xx
	public String edit(@PathVariable("id") Integer id, Model model) {		
		Book book=bookRepository.getReferenceById(id);  //lo recupero dal DB
		
		List<Category> categoryList=categoryRepository.findAll();
		
		model.addAttribute("book", book);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("edit", true);
		return "books/edit";
	}
	
	@PostMapping("/edit/{id}")		//richieste POST del tipo /books/edit/xx
	public String update(
			@Valid @ModelAttribute Book formBook,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors())
			return "books/edit";
		
		//if (bookRepository.findByIsbn(formBook.getIsbn()).size()>0)
		//	System.out.println("Isbn duplicato");
		//else
			bookRepository.save(formBook);
		
		
		
		return "redirect:/books";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
	 
	   bookRepository.deleteById(id);
	   
	   return "redirect:/books";
	}
}
