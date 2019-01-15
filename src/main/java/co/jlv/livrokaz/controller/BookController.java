package co.jlv.livrokaz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.repository.AdresseRepository;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.ProfilAccessRepository;
import co.jlv.livrokaz.repository.PublisherRepository;
import co.jlv.livrokaz.repository.UserRepository;

@RestController
@RequestMapping("/livrokaz")
public class BookController {

	@Autowired
	AdresseRepository AdresseRepo;

	@Autowired
	AuthorRepository AuthorRepo;

	@Autowired
	GendleRepository GendleRepo;

	@Autowired
	GoogleBookRepository GoogleBookRepo;

	@Autowired
	ProfilAccessRepository ProfilAccessRepo;

	@Autowired
	PublisherRepository PublisherRepo;

	@Autowired
	UserRepository UserRepo;

	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks() {
		List<GoogleBook> listeLivres = null;
		try {
			listeLivres = (List<GoogleBook>) GoogleBookRepo.findAll();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listeLivres);
	}
	
//	@PostMapping("/author")
//	public ResponseEntity<?> addAuthor(@RequestBody Author author) {
//		Author resultAuthor = null;
//		
		
	}

