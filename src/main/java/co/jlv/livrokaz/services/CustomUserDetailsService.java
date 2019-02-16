package co.jlv.livrokaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.jlv.livrokaz.model.Users;
import co.jlv.livrokaz.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username);
        if(users == null)
            new UsernameNotFoundException("Utilisateur introuvable !!!");
        return users;
    }

    @Transactional
    public Users loadUserById(Long id){
        Users users = usersRepository.getByUsersId(id);
        if(users == null)
            new UsernameNotFoundException("Utilisateur introuvable !!!");
        return users;
    }
}
