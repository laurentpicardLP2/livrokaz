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
	public ResponseEntity<?> addUsers(@Valid String gendleType1, @Valid String gendleType2) {
		Users users;
		Authorities authorities;
		Adresse adresse, newAdresse;
		List<Adresse> adresses = new ArrayList<Adresse>();
		Date dateBirthday;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		newAdresse = new Adresse("50", "Rue Marx Dormoy", 92260, "Fontenay-aux-roses", "France");
		adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		if (adresse == null) {
			adresseRepo.save(newAdresse);
			adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		}
		adresses.add(adresse);
		dateBirthday = Date.from((LocalDate.of(1972, 5, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users("dbuser","{noop}books", true, adresses, dateBirthday);
		try {
			usersRepo.save(users);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		} 
		authorities = new Authorities(users, "ROLE_USER");
		try {
			authoritiesRepo.save(authorities);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
		
		/*if (gendle == null) {
			gendleRepo.save(new Gendle(gendleType1));
			gendleRepo.save(new Gendle(gendleType2));
			return ResponseEntity.status(HttpStatus.OK).body(gendleRepo.findByGendle(gendleType1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}*/
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	

	
}
