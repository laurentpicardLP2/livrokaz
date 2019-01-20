package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable {
	
	@Id
	private String username;

	//bi-directional many-to-one association to Authorities
	@OneToMany(mappedBy="users")
	private List<Authorities> authorities;
	
	private  String password;
	
	private boolean enabled;
	
	private String civility;

	public Users() {
	}
	
	public Users(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled; 
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getCivility() {
		return this.civility;
	}

	public void setCivility(String civility) {
		this.civility = civility;
	}

	public List<Authorities> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(List<Authorities> authorities) {
		this.authorities = authorities;
	}

	public Authorities addAuthorities(Authorities authorities) {
		getAuthorities().add(authorities);
		authorities.setUsers(this);

		return authorities;
	}

	public Authorities removeAuthorities(Authorities authorities) {
		getAuthorities().remove(authorities);
		authorities.setUsers(null);

		return authorities;
	}

}