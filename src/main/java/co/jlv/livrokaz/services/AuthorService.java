package co.jlv.livrokaz.services;

import java.util.List;

import co.jlv.livrokaz.model.Author;

public interface AuthorService {
	
	public List<Author> getAllAuthors();
	
	public Author saveAuthor(Author author);
	
	public void deleteAuthor(Author author);

}
