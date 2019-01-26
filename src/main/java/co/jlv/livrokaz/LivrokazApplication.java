package co.jlv.livrokaz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import co.jlv.livrokaz.model.Adresse;
import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.model.Publisher;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.AdresseRepository;
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
	private AdresseRepository adresseRepo;


	public static void main(String[] args) throws Exception, JSONException, MalformedURLException, IOException, ClassNotFoundException, SQLException {
		SpringApplication.run(LivrokazApplication.class, args);
	}

	@Override
	public void run(String... args)
			throws Exception, JSONException, MalformedURLException, IOException, ClassNotFoundException, SQLException {
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
		
		dateBirthday = Date.from((LocalDate.of(2010, 8, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users("dbdevelopper", "{bcrypt}" + bcrypt.encode("books"), true, adresses, dateBirthday);
		try {
			usersRepo.save(users);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		} 
		authorities = new Authorities(users, "ROLE_DEVELOPPER");
		try {
			authoritiesRepo.save(authorities);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
		
		adresses.clear();
		newAdresse = new Adresse("50", "Rue Marx Dormoy", 92260, "Fontenay-aux-roses", "France");
		adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		if (adresse == null) {
			adresseRepo.save(newAdresse);
			adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		}
		adresses.add(adresse);		
		newAdresse = new Adresse("6", "Chemin de l'étang", 45260, "Châtenoy", "France");
		adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		if (adresse == null) {
			adresseRepo.save(newAdresse);
			adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		}
		adresses.add(adresse);
		dateBirthday = Date.from((LocalDate.of(2012, 1, 9).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users("dbmanager", "{bcrypt}" + bcrypt.encode("books"), true, adresses, dateBirthday);
		try {
			usersRepo.save(users);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		} 
		authorities = new Authorities(users, "ROLE_MANAGER");
		try {
			authoritiesRepo.save(authorities);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
		
		adresses.clear();
		newAdresse = new Adresse("5", "Rue Molière", 92400, "Courbevoie", "France");
		adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		if (adresse == null) {
			adresseRepo.save(newAdresse);
			adresse = adresseRepo.findByAdresse(newAdresse.getNumVoie(), newAdresse.getNomVoie(), newAdresse.getCodePostal(), newAdresse.getCity(), newAdresse.getCountry());
		}
		adresses.add(adresse);
		dateBirthday = Date.from((LocalDate.of(2013, 12, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		users = new Users("dbadmin", "{bcrypt}" + bcrypt.encode("books"), true, adresses, dateBirthday);
		try {
			usersRepo.save(users);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
		authorities = new Authorities(users, "ROLE_ADMIN");
		try {
			authoritiesRepo.save(authorities);
		} catch(Exception e) {
			//TODO : gestion d'un utilisateur déjà existant
		}
	}
		



}
