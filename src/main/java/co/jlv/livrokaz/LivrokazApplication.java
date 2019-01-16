package co.jlv.livrokaz;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.model.Publisher;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;



@SpringBootApplication
public class LivrokazApplication implements CommandLineRunner {
	
	@Autowired
	private GoogleBookRepository googleBookRepo;
	
	@Autowired
	private GendleRepository gendleRepo;
	
	@Autowired
	private PublisherRepository publisherRepo;
	
	@Autowired
	private AuthorRepository authorRepo;

    public static void main(String[] args) {
        SpringApplication.run(LivrokazApplication.class, args);
    }
    
    
	@Override
	public void run(String... args) throws Exception {
		GoogleBook gb;
		Gendle gendle;
		Publisher publisher;
		Author author;
		List<Author> authors = new ArrayList<Author>();
		String sDate;
		int availableQuantity;
		String categorie;
		String codeISBN;
		String description;
		String imgThumbnail;
		boolean isEbook;
		String langage;
		int pageCount;
		double price;
		String textSnippet;
		String title;

		
		// Clean up database tables
    	//googleBookRepo.deleteAllInBatch();
		
		gendle=gendleRepo.findByGendle("Roman");
		if(gendle==null) {
			gendleRepo.save(new Gendle("Roman"));
		}
		gendle=gendleRepo.findByGendle("Documentaire");
		if(gendle==null) {
			gendleRepo.save(new Gendle("Documentaire"));
		}
		gendle=gendleRepo.findByGendle("Philosophie");
		if(gendle==null) {
			gendleRepo.save(new Gendle("Philosophie"));
		}
	
		
		publisher = publisherRepo.findByPublisher("Les chevaliers de la rosette");
		if(publisher == null) {
			publisherRepo.save(new Publisher("Les chevaliers de la rosette"));
		}
		publisher = publisherRepo.findByPublisher("Les éditions du placard");
		if(publisher == null) {
			publisherRepo.save(new Publisher("Les éditions du placard"));
		}
		publisher = publisherRepo.findByPublisher("Les éditions du seuil");
		if(publisher == null) {
			publisherRepo.save(new Publisher("Les éditions du seuil"));
		}
		
		author = authorRepo.findByAuthor("Victor Hugo");
		if(author == null) {
			authorRepo.save(new Author("Victor Hugo"));
		}
		author = authorRepo.findByAuthor("Honore de Balzac");
		if(author == null) {
			authorRepo.save(new Author("Honore de Balzac"));
		}
		author = authorRepo.findByAuthor("Emile Zola");
		if(author == null) {
			authorRepo.save(new Author("Emile Zola"));
		}
		
		
		gendle = gendleRepo.findByGendle("Philosophie");
		publisher = publisherRepo.findByPublisher("Les chevaliers de la rosette");
		authors.add(authorRepo.findByAuthor("Victor Hugo"));
		authors.add(authorRepo.findByAuthor("Honore de Balzac"));
		
		
		/*Date myDate = new Date(0);
		System.out.println(myDate);
		System.out.println(new SimpleDateFormat("MM-dd-yyyy").format(myDate));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
		System.out.println(myDate);*/
		
		
		

		//java.sql.Date sDate = new java.sql.Date(new java.util.Date().getTime());

		
		sDate="1895-01-20";
		availableQuantity = 10;
		categorie = "Cuisine";
		codeISBN = "12-AZE444";
		description = "description"; 
		imgThumbnail = "http://imgThumbnail";
		isEbook = false;
		langage = "fr";
		pageCount = 370;
		price = 34.50;
		textSnippet = "textSnippet";
		title = "title";
		
		
		gb = new GoogleBook(gendle, publisher, authors, sDate, 
				availableQuantity, categorie, codeISBN, description, imgThumbnail,
				isEbook, langage, pageCount, price, textSnippet, title);
		googleBookRepo.save(gb);
		/*List<Author>authorsVerif = gb.getAuthors();
		for(Author a : authorsVerif) {
			System.out.println(a.getFullName());
		}*/
		
	}
}
