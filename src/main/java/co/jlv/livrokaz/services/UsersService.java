package co.jlv.livrokaz.services;

import java.util.List;

import co.jlv.livrokaz.model.Users;

public interface UsersService {
	
public List<Users> getAllUsers();
	
	public Users saveUsers(Users users);
	
	public void deleteUsers(Users users);


}
