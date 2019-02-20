package co.jlv.livrokaz.services;

import java.util.List;

import co.jlv.livrokaz.model.GoogleBook;

public interface GoogleBookService {

	public List<GoogleBook> getAllGoogleBooks();
	
	public GoogleBook saveGoogleBook(GoogleBook googleBook);
	
	public void deleteGoogleBook(GoogleBook googleBook);
}
