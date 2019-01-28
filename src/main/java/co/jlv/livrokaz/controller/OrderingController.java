package co.jlv.livrokaz.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.OrderingRepository;

@RestController
@RequestMapping("/livrokaz")
public class OrderingController {
	
	@Autowired
	GoogleBookRepository googleBookRepo;
	
	@Autowired
	OrderingRepository orderingRepo;

	
	@PostMapping("/orderbook")
	public ResponseEntity<?> orderBook(@Valid int idBook) throws Exception {
		GoogleBook gb = null;
		try {
			gb = googleBookRepo.findById(idBook).get();
			return ResponseEntity.status(HttpStatus.OK).body(gb);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
		
	}
}
