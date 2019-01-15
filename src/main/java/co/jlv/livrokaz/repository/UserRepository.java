package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.jlv.livrokaz.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
