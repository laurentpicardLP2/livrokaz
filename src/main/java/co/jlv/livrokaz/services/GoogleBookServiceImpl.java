package co.jlv.livrokaz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {
	private GoogleBookRepository googleBookRepo;

    public GoogleBookServiceImpl(GoogleBookRepository googleBookRepo) {
        this.googleBookRepo = googleBookRepo;
    }


    public List<GoogleBook> getAllGoogleBook() {
        // TODO Auto-generated method stub
        return this.googleBookRepo.findAll();
    }
    
    public GoogleBook saveGoogleBook(GoogleBook googleBook) {
		return this.googleBookRepo.save(googleBook);
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
