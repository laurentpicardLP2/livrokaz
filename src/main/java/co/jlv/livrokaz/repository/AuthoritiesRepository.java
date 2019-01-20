package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.jlv.livrokaz.model.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String>{

}
