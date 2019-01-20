package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the Adresse database table.
 * 
 */
@Entity
@NamedQuery(name="Adresse.findAll", query="SELECT a FROM Adresse a")
public class Adresse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int adresseId;

	private String numVoie;

	private String nomVoie;

	private int codePostal;

	private String city;

	private String country;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="adresses")
	private List<Users> users;

	public Adresse() {
	}
	
	public Adresse( String numVoie, String nomVoie, int codePostal, String city, String country) {
		this.numVoie = numVoie;
		this.nomVoie = nomVoie;
		this.codePostal = codePostal;
		this.city = city;
		this.country = country;
	}

	public int getAdresseId() {
		return this.adresseId;
	}

	public void setAdresseId(int adresseId) {
		this.adresseId = adresseId;
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

	public List<Users> getUsers() {
		return this.users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

}