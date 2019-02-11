package co.jlv.livrokaz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.repository.AuthorRepository;


@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepo;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepo = authorRepository;
    }


    public List<Author> getAllAuthors() {
        // TODO Auto-generated method stub
        return this.authorRepo.findAll();
    }
    
    public Author saveAuthor(Author author) {
		return this.authorRepo.save(author);
	}
    
   
}