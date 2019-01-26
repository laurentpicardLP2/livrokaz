package co.jlv.livrokaz.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


/**
 * The persistent class for the AdresseLivraison database table.
 * 
 */
@Entity
@NamedQuery(name="AdresseLivraison.findAll", query="SELECT a FROM AdresseLivraison a")
public class AdresseLivraison  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "username")
	private String username;

	private String numVoie;

	private String nomVoie;

	private int codePostal;

	private String city;

	private String country;

	//one-to-one-directional association to Users
	@MapsId()
	@OneToOne(optional=true, cascade=CascadeType.REMOVE)
	@JoinColumn(name="username", unique=true, nullable=false, updatable=false)
    private Users users;


	public AdresseLivraison() {
	}
	
	public AdresseLivraison(Users users, String numVoie, String nomVoie, int codePostal, String city, String country) {
		super();
		
		this.users = users;
		this.numVoie = numVoie;
		this.nomVoie = nomVoie;
		this.codePostal = codePostal;
		this.city = city;
		this.country = country;
	}

	public String getNumVoie() {
		return this.numVoie;
	}

	public void setNumVoie(String numVoie) {
		this.numVoie = numVoie;
	}

	public String getNomVoie() {
		return this.nomVoie;
	}

	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}

	public int getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
}

