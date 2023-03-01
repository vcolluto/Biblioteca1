package org.java.lessons.biblioteca.controller;

import java.util.List;

import org.java.lessons.biblioteca.model.Category;
import org.java.lessons.biblioteca.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping()		// GET /categories
	public String index(			
			Model model) {	
		List<Category> res = categoryRepository.findAll(Sort.by("name"));	//tutti le categorie ordinate per nome
		model.addAttribute("elencoCategorie", res);
		return "categories/index";
	}

	@GetMapping("/create")		//gestirà le richieste GET di tipo /categories/create
	public String create(Model model) {
		Category category=new Category();	//non esiste ancora sul DB
		
		model.addAttribute("category", category);
	
		return "categories/edit";
	}
	
	@PostMapping("/create")  	//gestirà le richieste di tipo POST di tipo /books/create
	public String store(
		@Valid @ModelAttribute("category") Category formCategory, 
		BindingResult bindingResult,
		Model model){
	
		if (bindingResult.hasErrors())
			return "categories/edit";
		
		categoryRepository.save(formCategory);
		
		return "redirect:/categories"; //genera un altro get
		
	}
}
