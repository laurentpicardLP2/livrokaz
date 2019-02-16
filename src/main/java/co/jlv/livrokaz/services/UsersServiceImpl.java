package co.jlv.livrokaz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepo;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepo = usersRepository;
    }


    public List<Users> getAllUsers() {
        // TODO Auto-generated method stub
        return this.usersRepo.findAll();
    }
    
    public Users saveUsers(Users users) {
		return this.usersRepo.save(users);
	}
    
    public void deleteUsers(Users users) {
    	this.usersRepo.deleteByUsername(users.getUsername());
    }
    
   
}
