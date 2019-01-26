package co.jlv.livrokaz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import co.jlv.livrokaz.model.AdresseDomicile;
import co.jlv.livrokaz.model.AdresseLivraison;
import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.model.Publisher;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AdresseDomicileRepository;
import co.jlv.livrokaz.repository.AdresseLivraisonRepository;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.AuthoritiesRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;
import co.jlv.livrokaz.repository.UsersRepository;

@SpringBootApplication
public class LivrokazApplication implements CommandLineRunner {


	@Autowired
	private AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private AdresseDomicileRepository adresseDomicileRepo;
	
	@Autowired
	private AdresseLivraisonRepository adresseLivraisonRepo;


	public static void main(String[] args) throws Exception, MalformedURLException, IOException, ClassNotFoundException, SQLException {
		SpringApplication.run(LivrokazApplication.class, args);
	}

	@Override
	public void run(String... args)
			throws Exception,  MalformedURLException, IOException, ClassNotFoundException, SQLException {
		Users users;
		Authorities authorities;
		AdresseDomicile adresseDomicile;
		AdresseLivraison adresseLivraison;
		Date dateBirthday;		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		dateBirthday = Date.from((LocalDate.of(2013, 12, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users("dbadmin", "{bcrypt}" + bcrypt.encode("books"), true, dateBirthday);

		adresseDomicile = new AdresseDomicile(users, "5", "Rue Molière", 92400, "Courbevoie", "France");
				
		try {
			adresseDomicileRepo.save(adresseDomicile);			
		} catch(Exception e) {
			
		}
		adresseLivraison = new AdresseLivraison(users, "5", "Rue Molière", 92400, "Courbevoie", "France");
		try {
			adresseLivraisonRepo.save(adresseLivraison);
		} catch(Exception e) {
			
		}
		authorities = new Authorities(users, "ROLE_ADMIN");
		try {
			authoritiesRepo.save(authorities);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
		
		try {
			usersRepo.save(users);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}

	}
		



}
