package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.model.Gendle;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String>{
	@Query("SELECT a FROM Authorities a WHERE a.username LIKE %?1%")
	 public Authorities findByUsername(String name);
	
	void deleteByUsername(String username);
	

}
