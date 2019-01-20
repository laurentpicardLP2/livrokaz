package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
//	@OneToMany(mappedBy="users")
//	private List<Authorities> authorities;
	
	//bi-directional many-to-one association to Ordering
	@OneToMany(mappedBy="users")
	private List<Ordering> orderings;
	
	
	private  String password;
	
	private boolean enabled;
	
	private String civility;

	@Temporal(TemporalType.DATE)
	private Date dateBirthday;

	private String firstName;

	private String lastName;

	private String tel;
	
	//bi-directional many-to-many association to Adresse
	@ManyToMany
	@JoinTable(
		name="R_Users_Adresse"
		, joinColumns={
			@JoinColumn(name="username")
			}
		, inverseJoinColumns={
			@JoinColumn(name="adresseId")
			}
		)
	private List<Adresse> adresses;


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

	public Date getDateBirthday() {
		return this.dateBirthday;
	}

	public void setDateBirthday(Date dateBirthday) {
		this.dateBirthday = dateBirthday;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
//	public List<Authorities> getAuthorities() {
//	return this.authorities;
//}
//
//public void setAuthorities(List<Authorities> authorities) {
//	this.authorities = authorities;
//}



//	public Authorities addAuthorities(Authorities authorities) {
//		getAuthorities().add(authorities);
//		authorities.setUsers(this);
//
//		return authorities;
//	}
//
//	public Authorities removeAuthorities(Authorities authorities) {
//		getAuthorities().remove(authorities);
//		authorities.setUsers(null);
//
//		return authorities;
//	}
//	
	public List<Ordering> getOrderings() {
		return this.orderings;
	}

	public void setOrderings(List<Ordering> orderings) {
		this.orderings = orderings;
	}

	public Ordering addOrdering(Ordering ordering) {
		getOrderings().add(ordering);
		ordering.setUsers(this);

		return ordering;
	}

	public Ordering removeOrdering(Ordering ordering) {
		getOrderings().remove(ordering);
		ordering.setUsers(null);

		return ordering;
	}

	public List<Adresse> getAdresses() {
		return this.adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}
	


}