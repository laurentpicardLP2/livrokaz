package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Gendle;

public interface GendleRepository extends JpaRepository<Gendle, Integer>{
	@Query("SELECT g FROM Gendle g WHERE g.typeGendle LIKE %?1%")
	  public Gendle findByGendle(String name);
}
