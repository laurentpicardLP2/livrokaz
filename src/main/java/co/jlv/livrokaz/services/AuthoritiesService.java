package co.jlv.livrokaz.services;

import java.util.List;

import co.jlv.livrokaz.model.Authorities;

public interface AuthoritiesService {
	
	public List<Authorities> getAllAuthorities();
	
	public Authorities saveAuthorities(Authorities authorities);
	
	public void deleteAuthorities(Authorities authorities);


}

