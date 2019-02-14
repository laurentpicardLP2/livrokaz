package co.jlv.livrokaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.jlv.livrokaz.model.Users;

public interface UsersRepository extends JpaRepository<Users, String>{
	Users findByUsername(String username);
    Users getById(Long id);
}
