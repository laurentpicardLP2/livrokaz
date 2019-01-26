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

import co.jlv.livrokaz.model.Adresse;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AdresseRepository;
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
	AdresseRepository adresseRepo;
	
	@PostMapping("/users")
	public ResponseEntity<?> addUsers(@Valid String numVoie, @Valid String nomVoie, @Valid int cp, @Valid String city, @Valid String country, 
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String nameUser, @Valid String pwd, @Valid String typeRole) {
		Users users;
		Authorities authorities;
		Adresse adresse, newAdresse;
		List<Adresse> adresses = new ArrayList<Adresse>();
		Date dateBirthday;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		newAdresse = new Adresse(numVoie, nomVoie, cp, city, country);
		adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		if (adresse == null) {
			adresseRepo.save(newAdresse);
			adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		}
		adresses.add(adresse);
		dateBirthday = Date.from((LocalDate.of(yyyy, mm, dd).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users(nameUser,"{bcrypt}" + bcrypt.encode(pwd) , true, adresses, dateBirthday);
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
