package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.jlv.livrokaz.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
