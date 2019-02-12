package co.jlv.livrokaz.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.services.AuthorService;
import co.jlv.livrokaz.services.GestionCategories;


@RestController
@RequestMapping("/livrokaz")
@CrossOrigin("http://localhost:4200")
public class AuthorController {
	
	private AuthorService authorService;
	
	@Autowired
	AuthorRepository authorRepo;
	
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}
	
	@GetMapping("/authors")
	public List<Author> getAllAuthors() {
		return this.authorService.getAllAuthors();

	}
	
	@PostMapping("/addauthor")
	public ResponseEntity<?> creataAuthor(@RequestBody Author newAuthor) {
				
		try {
			return ResponseEntity.ok(this.authorService.saveAuthor(newAuthor)) ;
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
				
	}
	
	/**
	 * Fonction de mise à jour d'un auteur.
	 * 
	 * @param timeline le auteur à mettre à jour
	 * @return le auteur mis à jour par la base si réussite, code 400 sinon
	 */
	@PutMapping("/updateauthor")
	public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
		/*
		 * Pour faire propre si on réussi, on renvoie le nouvel auteur avec un code
		 * 200. Sinon on renvoie un code d'erreur 400
		 */
		try {
			return ResponseEntity.ok(this.authorService.saveAuthor(author));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@PutMapping("/delauthor")
    public ResponseEntity<?> delUser(@RequestBody Author author) {
		this.authorService.deleteAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(this.authorService.getAllAuthors());
    }
	

}
