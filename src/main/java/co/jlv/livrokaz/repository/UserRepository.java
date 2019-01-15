package co.jlv.livrokaz.repository;

import org.springframework.data.repository.CrudRepository;

import co.jlv.livrokaz.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
