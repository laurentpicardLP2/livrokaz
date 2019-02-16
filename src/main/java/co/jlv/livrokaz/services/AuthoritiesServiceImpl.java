package co.jlv.livrokaz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.jlv.livrokaz.model.Authorities;
import co.jlv.livrokaz.repository.AuthoritiesRepository;

@Service
public class AuthoritiesServiceImpl  implements AuthoritiesService {
	private AuthoritiesRepository authoritiesRepo;

    public AuthoritiesServiceImpl(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepo = authoritiesRepository;
    }


    public List<Authorities> getAllAuthorities() {
        // TODO Auto-generated method stub
        return this.authoritiesRepo.findAll();
    }
    
    public Authorities saveAuthorities(Authorities authorities) {
    	System.out.println("authorities ");
    	System.out.println("authorities " + authorities.getAuthority());
    	System.out.println("authorities " + authorities.getAuthority() + " " + authorities.getUsers().getUsername());
		return this.authoritiesRepo.save(authorities);
	}
    
    public void deleteAuthorities(Authorities authorities) {
    	this.authoritiesRepo.deleteByUsername(authorities.getUsers().getUsername());
    }
}
