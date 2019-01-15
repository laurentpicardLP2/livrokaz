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


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;

	private String civility;

	@Temporal(TemporalType.DATE)
	private Date dateBirthday;

	private String firstName;

	private String lastName;

	private String mail;

	private String password;

	private int profilId;

	private String tel;

	//bi-directional many-to-one association to Ordering
	@OneToMany(mappedBy="user")
	private List<Ordering> orderings;

	//bi-directional many-to-one association to ProfilAccess
	@ManyToOne
	@JoinColumn(name="profilAccessId")
	private ProfilAccess profilAccess;

	//bi-directional many-to-many association to Adresse
	@ManyToMany
	@JoinTable(
		name="R_User_Adresse"
		, joinColumns={
			@JoinColumn(name="userId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="adresseId")
			}
		)
	private List<Adresse> adresses;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getProfilId() {
		return this.profilId;
	}

	public void setProfilId(int profilId) {
		this.profilId = profilId;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<Ordering> getOrderings() {
		return this.orderings;
	}

	public void setOrderings(List<Ordering> orderings) {
		this.orderings = orderings;
	}

	public Ordering addOrdering(Ordering ordering) {
		getOrderings().add(ordering);
		ordering.setUser(this);

		return ordering;
	}

	public Ordering removeOrdering(Ordering ordering) {
		getOrderings().remove(ordering);
		ordering.setUser(null);

		return ordering;
	}

	public ProfilAccess getProfilAccess() {
		return this.profilAccess;
	}

	public void setProfilAccess(ProfilAccess profilAccess) {
		this.profilAccess = profilAccess;
	}

	public List<Adresse> getAdresses() {
		return this.adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}

}