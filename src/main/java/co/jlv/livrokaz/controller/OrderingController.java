package co.jlv.livrokaz.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.OrderingRepository;
import co.jlv.livrokaz.services.IAuthenticationFacade;

@RestController
@RequestMapping("/orderctrl")
public class OrderingController {
	
	@Autowired
	GoogleBookRepository googleBookRepo; 
	
	@Autowired
	OrderingRepository orderingRepo;
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;

	
	@PostMapping("/book")
	public ResponseEntity<?> orderBook(@Valid int idBook, HttpSession session) throws Exception {
		if (UserController.sessionUser == null) {
			UserController.sessionUser = session;
			Authentication authentication = authenticationFacade.getAuthentication();
			UserController.username = authentication.getName();
			System.out.println(UserController.username + " " + UserController.sessionUser.getId());
		}
		
		
		
		GoogleBook gb = null;
		try {
			gb = googleBookRepo.findById(idBook).get();
			return ResponseEntity.status(HttpStatus.OK).body(gb);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
		
	}
}
