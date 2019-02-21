package co.jlv.livrokaz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.model.Publisher;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {
	private GoogleBookRepository googleBookRepo;
	private GendleRepository gendleRepo;
	private PublisherRepository publisherRepo;
	private AuthorRepository authorRepo;
	private List<Author> authors;
	private Author author;
	private Gendle gendle;
	private Publisher publisher;

    public GoogleBookServiceImpl(GoogleBookRepository googleBookRepo, AuthorRepository authorRepo, GendleRepository gendleRepo, PublisherRepository publisherRepo) {
        this.googleBookRepo = googleBookRepo;
        this.gendleRepo = gendleRepo;
        this.publisherRepo = publisherRepo;
        this.authorRepo = authorRepo;
        
        this.authors = new ArrayList<Author>();
    }


    public List<GoogleBook> getAllGoogleBook() {
        // TODO Auto-generated method stub
    	
        return this.googleBookRepo.findAll();
    }
    
    public GoogleBook saveGoogleBook(GoogleBook googleBook, String lstAuthors, String gendle, String publisher) {
    	authors.clear();
    	String[] authorSplit = lstAuthors.split(",");

    	for (int i = 0; i < authorSplit.length; i++) {
    		
			this.author = this.authorRepo.findByAuthor(authorSplit[i]);
			if (this.author == null) {
				System.out.println("authorSplit[i]" + authorSplit[i]);
				this.authorRepo.save(new Author(authorSplit[i]));
				
			}
			try {
				this.authors.add(authorRepo.findByAuthor(authorSplit[i]));
			} catch (Exception e) {
				this.authors = null;
			}
		}
    	
    	this.gendle = gendleRepo.findByGendle(gendle);
		if (this.gendle == null) {
			this.gendleRepo.save(new Gendle(gendle, 1));
			this.gendle = this.gendleRepo.findByGendle(gendle);
		} else {
			this.gendle.setNbBooks(this.gendle.getNbBooks() + 1);
			this.gendleRepo.save(this.gendle);
		}
		
		this.publisher = this.publisherRepo.findByPublisher(publisher);
		if (this.publisher == null) {
			this.publisherRepo.save(new Publisher(publisher));
			this.publisher = this.publisherRepo.findByPublisher(publisher);
		}
		
		for(Author a : authors) {
			System.out.println("this.authors : " + a);
		}
		
		return this.googleBookRepo.save(new GoogleBook(
				this.gendle, 
				this.publisher, 
				this.authors, 
				googleBook.getPublishReleased(), 
				googleBook.getAvailableQuantity(), 
				googleBook.getCategorie(), 
				googleBook.getCodeIsbn(),
				googleBook.getDescription(), 
				googleBook.getImgThumbnail(), 
				googleBook.getIsEbook(), 
				googleBook.getLangage(), 
				googleBook.getPageCount(), 
				googleBook.getPrice(), 
				googleBook.getTextSnippet(), 
				googleBook.getTitle()
		));
	}
    
    public void deleteGoogleBook(GoogleBook googleBook) {
    	this.googleBookRepo.deleteById(Integer.valueOf(googleBook.getBookId()));
    }


	@Override
	public List<GoogleBook> getAllGoogleBooks() {
		// TODO Auto-generated method stub
		return null;
	}
}
