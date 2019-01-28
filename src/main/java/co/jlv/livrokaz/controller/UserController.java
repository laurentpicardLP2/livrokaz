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

import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.UsersRepository;

@RestController
@RequestMapping("/livrokaz")
public class UserController {
	
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@PostMapping("/users")
	public ResponseEntity<?> addUsers(@Valid String numVoieDomicile, @Valid String nomVoieDomicile, @Valid int cpDomicile,
		@Valid String cityDomicile, @Valid String countryDomicile, @Valid String numVoieLivraison,
		@Valid String nomVoieLivraison, @Valid int cpLivraison, @Valid String cityLivraison, @Valid String countryLivraison,
		@Valid int yyyy, @Valid int mm, @Valid int dd, @Valid String userName, @Valid String pwd, @Valid String typeRole,
		@Valid String civility, @Valid String firstName, @Valid String lastName, @Valid String tel) {
		Users users;
		Authorities authorities;
		Date dateBirthday;

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		
		dateBirthday = Date.from((LocalDate.of(yyyy, mm, dd).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users(userName,"{bcrypt}" + bcrypt.encode(pwd) , true, 
				civility, firstName, lastName, tel, dateBirthday, 
				numVoieDomicile, nomVoieDomicile, cpDomicile, cityDomicile, countryDomicile,
				numVoieLivraison, nomVoieLivraison, cpLivraison, cityLivraison, countryLivraison
				);
		
	
		authorities = new Authorities(typeRole);
		authorities.setUsers(users);
		authoritiesRepo.save(authorities);
		try {
			//authoritiesRepo.save(authorities);
			return ResponseEntity.status(HttpStatus.OK).body(null);
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
		
				
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login() {
		System.out.println("****************************************");
		
		try {
			//authoritiesRepo.save(authorities);
			return ResponseEntity.status(HttpStatus.OK).body("<html> Session</html>");
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
		
				
	}
	
	

	
}
