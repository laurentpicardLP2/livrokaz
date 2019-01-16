package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
	@Query("SELECT p FROM Publisher p WHERE p.namePublisher LIKE %?1%")
	  public Publisher findByPublisher(String name);
}
