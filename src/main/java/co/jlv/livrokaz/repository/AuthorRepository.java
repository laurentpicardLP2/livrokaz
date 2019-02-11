package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{
	@Query("SELECT a FROM Author a WHERE a.fullName LIKE %?1%")
	  public Author findByAuthor(String name);
	
	
	
	
}
