package co.jlv.livrokaz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.services.AuthorService;

@RestController
@RequestMapping("/livrokaz")
public class AuthorController {
	
	private AuthorService authorService;
	
	@Autowired
	AuthorRepository authorRepo;
	
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}
	
	@GetMapping("/authors")
	public List<Author> getAllAuthors() {
		List<Author> authors = null;
		try {
			authors = (List<Author>) authorRepo.findAll();
			
		} catch (Exception e) {
			return null;
		}
		return this.authorService.getAllAuthors();

	}
	

}
