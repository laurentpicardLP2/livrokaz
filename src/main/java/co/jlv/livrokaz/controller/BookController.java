package co.jlv.livrokaz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.repository.AdresseRepository;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;


@RestController
@RequestMapping("/livrokaz")
public class BookController {

	@Autowired
	AdresseRepository adresseRepo;

	@Autowired
	AuthorRepository authorRepo;

	@Autowired
	GendleRepository gendleRepo;

	@Autowired
	GoogleBookRepository googleBookRepo;

	@Autowired
	PublisherRepository publisherRepo;

	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks() {
		List<GoogleBook> listeLivres = null;
		try {
			listeLivres = (List<GoogleBook>) googleBookRepo.findAll();
			//System.out.println(listeLivres.get(0).getAuthors().get(0));
			

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listeLivres);
	}
	
	@GetMapping("/authors/{bookId}")
	public ResponseEntity<?> getAuthorsByBook(@PathVariable Integer bookId) {
		List<Author> listeLivres = null;
		try {
			Optional<GoogleBook> gbOpt = googleBookRepo.findById(bookId);
			if(gbOpt.isPresent()) {
				GoogleBook gbEff = gbOpt.get();
				listeLivres = gbEff.getAuthors();
			}
			//listeLivres = (List<GoogleBook>) GoogleBookRepo.findAll();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listeLivres);
	}
	
	@PostMapping("/gendle")
	public ResponseEntity<?> addGendle(@Valid String gendleType1, @Valid String gendleType2) {
		Gendle gendle = gendleRepo.findByGendle(gendleType1);
		if (gendle == null) {
			gendleRepo.save(new Gendle(gendleType1));
			gendleRepo.save(new Gendle(gendleType2));
			return ResponseEntity.status(HttpStatus.OK).body(gendleRepo.findByGendle(gendleType1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	/**
	 * supprimer une référence d'un livre
	 */
	@DeleteMapping("/delbook/{delId}")
    public ResponseEntity<?> delUser(@PathVariable Integer delId) {
       
        googleBookRepo.deleteById(delId);
        
        List<GoogleBook> listeLivres = null;
		try {
			listeLivres = (List<GoogleBook>) googleBookRepo.findAll();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listeLivres);
        
    }
	
	
	/**
	 * Mettre à jour
	 */
	@PutMapping(value = "/updateBook/{newData}")
	public ResponseEntity<?> updateApprenant(@PathVariable String newData) throws Exception {
		GoogleBook gbEff = null;
		
        String title = "";
        double price = 0;
        Integer id;
		
        Optional<GoogleBook> gbOpt = null;

        String[] parts = newData.split("&");
        for (String part : parts) {
            String[] subParts = part.split("=");
            if(subParts[0].equals("title")) {
                title = subParts[1];
            } else if(subParts[0].equals("price")) {
                price = Double.parseDouble(subParts[1]);
            } else if(subParts[0].equals("id")) {
                id = Integer.valueOf(subParts[1]);
                gbOpt = googleBookRepo.findById(id);
            }
        }
        
        if(gbOpt.isPresent()) {
        	gbOpt.get().setTitle(title);
			gbOpt.get().setPrice(price);
        }
        
		
		try {
		
			gbEff = googleBookRepo.save(gbOpt.get());
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(gbEff);
	}
	

	
	
//	@PostMapping("/author")
//	public ResponseEntity<?> addAuthor(@RequestBody Author author) {
//		Author resultAuthor = null;
//		
		
}
