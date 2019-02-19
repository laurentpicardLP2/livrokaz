package co.jlv.livrokaz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;
import co.jlv.livrokaz.services.GendleService;
import co.jlv.livrokaz.services.GestionCategories;

@RestController
@RequestMapping("/livrokaz")
@CrossOrigin("http://localhost:4200")
public class GendleController {
	private GendleService gendleService;
	
	@Autowired
	private GendleRepository gendleRepo;
	
	@Autowired
	AuthorRepository authorRepo;

	@Autowired
	GoogleBookRepository googleBookRepo;

	@Autowired
	PublisherRepository publisherRepo;
	
	
	public GendleController(GendleService gendleService) {
		this.gendleService = gendleService;
	}
	
	@GetMapping("/gendles")
	public List<Gendle> getAllGendles() {
		return this.gendleService.getAllGendles();

	}
	
	@PostMapping("/newgendle")
	public ResponseEntity<?> createGendle(@RequestBody Gendle newGendle) {
		try {
			// par isbn : GestionCategories.addCategrorie(entryCat, nbBooks, "1439149739",  authorRepo, gendleRepo, googleBookRepo, publisherRepo);
			System.out.println("Appel avt addCategories");
			GestionCategories.addCategrorie(newGendle.getTypeGendle(), newGendle.getNbBooks(), "", authorRepo, gendleRepo, googleBookRepo, publisherRepo);
			return ResponseEntity.status(HttpStatus.OK).body(newGendle);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
