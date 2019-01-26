package co.jlv.livrokaz.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.jlv.livrokaz.model.AdresseDomicile;
import co.jlv.livrokaz.model.AdresseLivraison;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AdresseDomicileRepository;
import co.jlv.livrokaz.repository.AdresseLivraisonRepository;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.UsersRepository;

@RestController
@RequestMapping("/livrokaz")
public class UserController {
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	AdresseDomicileRepository adresseDomicileRepo;
	
	@Autowired
	AdresseLivraisonRepository adresseLivraisonRepo;
	
	@PostMapping("/users")
	public ResponseEntity<?> addUsers(@Valid String numVoieDomicile, @Valid String nomVoieDomicile, @Valid int cpDomicile,
		@Valid String cityDomicile, @Valid String countryDomicile, @Valid String numVoieLivraison,
		@Valid String nomVoieLivraison, @Valid int cpLivraison, @Valid String cityLivraison, @Valid String countryLivraison,
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String nameUser, @Valid String pwd, @Valid String typeRole) {
		Users users;
		Authorities authorities;
		AdresseDomicile adresseDomicile;
		AdresseLivraison adresseLivraison;
		Date dateBirthday;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		
		dateBirthday = Date.from((LocalDate.of(yyyy, mm, dd).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users(nameUser,"{bcrypt}" + bcrypt.encode(pwd) , true, dateBirthday);
		
		
		adresseDomicile = new AdresseDomicile(users, numVoieDomicile, nomVoieDomicile, cpDomicile, cityDomicile, countryDomicile);
		try {
			adresseDomicileRepo.save(adresseDomicile);
		} catch (Exception e) {
			
		}

		adresseLivraison = new AdresseLivraison(users, numVoieLivraison, nomVoieLivraison, cpLivraison, cityLivraison, countryLivraison);
		try {
			adresseLivraisonRepo.save(adresseLivraison);
		} catch (Exception e) {
			
		}

		
		authorities = new Authorities(users, typeRole);
		try {
			authoritiesRepo.save(authorities);
			int x=1;
			
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
			
		}
		
		
		try {
			usersRepo.save(users);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} 
	
		
	}
	

	
}
