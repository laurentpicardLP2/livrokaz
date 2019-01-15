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
 * The persistent class for the ProfilAccess database table.
 * 
 */
@Entity
@NamedQuery(name="ProfilAccess.findAll", query="SELECT p FROM ProfilAccess p")
public class ProfilAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int profilAccessId;

	private int codeProfil;

	private String libelleProfil;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="profilAccess")
	private List<User> users;

	public ProfilAccess() {
	}

	public int getProfilAccessId() {
		return this.profilAccessId;
	}

	public void setProfilAccessId(int profilAccessId) {
		this.profilAccessId = profilAccessId;
	}

	public int getCodeProfil() {
		return this.codeProfil;
	}

	public void setCodeProfil(int codeProfil) {
		this.codeProfil = codeProfil;
	}

	public String getLibelleProfil() {
		return this.libelleProfil;
	}

	public void setLibelleProfil(String libelleProfil) {
		this.libelleProfil = libelleProfil;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setProfilAccess(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setProfilAccess(null);

		return user;
	}

}