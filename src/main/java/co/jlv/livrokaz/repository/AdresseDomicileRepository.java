package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.AdresseDomicile;

public interface AdresseDomicileRepository extends JpaRepository<AdresseDomicile, String>{
//	@Query("SELECT a FROM Adresse a WHERE a.numVoie LIKE %?1% and a.nomVoie LIKE %?2%  and a.codePostal = ?3  and a.city LIKE %?4%  and a.country LIKE %?5%")
//	  public AdresseDomicile findByAdresse(String numVoie, String nomVoie, int codePostal, String city, String country);
}
