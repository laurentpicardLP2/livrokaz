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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int adresseId;

	private String city;

	private BigInteger codePostal;

	private String country;

	private String nomVoie;

	private String numVoie;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="adresses")
	private List<Users> users;

	public Adresse() {
	}

	public int getAdresseId() {
		return this.adresseId;
	}

	public void setAdresseId(int adresseId) {
		this.adresseId = adresseId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigInteger getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(BigInteger codePostal) {
		this.codePostal = codePostal;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNomVoie() {
		return this.nomVoie;
	}

	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}

	public String getNumVoie() {
		return this.numVoie;
	}

	public void setNumVoie(String numVoie) {
		this.numVoie = numVoie;
	}

	public List<Users> getUsers() {
		return this.users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

}