package co.jlv.livrokaz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/authors/{bookId}")
	public ResponseEntity<?> getAuthorsByBook(@PathVariable Integer bookId) {
		List<Author> listeLivres = null;
		try {
			Optional<GoogleBook> gbOpt = GoogleBookRepo.findById(bookId);
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
	
	/**
	 * supprimer jedi
	 */
	@DeleteMapping("/delbook/{delId}")
    public ResponseEntity<?> delUser(@PathVariable Integer delId) {
       
        GoogleBookRepo.deleteById(delId);
        
        List<GoogleBook> listeLivres = null;
		try {
			listeLivres = (List<GoogleBook>) GoogleBookRepo.findAll();

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
                gbOpt = GoogleBookRepo.findById(id);
            }
        }
        
        if(gbOpt.isPresent()) {
        	gbOpt.get().setTitle(title);
			gbOpt.get().setPrice(price);
        }
        
		
		try {
		
			gbEff = GoogleBookRepo.save(gbOpt.get());
			
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
