package co.jlv.livrokaz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;

public interface GendleRepository extends JpaRepository<Gendle, Integer>{
	@Query("SELECT g FROM Gendle g WHERE g.typeGendle LIKE %?1%")
	  public Gendle findByGendle(String name);
	
	public List<GoogleBook> findByGendleId(int gendleId);
	
}
