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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the GoogleBooks database table.
 * 
 */
@Entity
@Table(name="Authorities")
@NamedQuery(name="Authorities.findAll", query="SELECT a FROM Authorities a")
public class Authorities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int authorityId;
	 
	private String authority;


	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="username")
	private Users users;

	
	
	public Authorities( 
			Users users, String authority) {
		super();
		
		this.users = users;
		this.authority = authority;
	}

	public Authorities() {
	}
	
	public int getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}

	
	
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@JsonIgnore
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


}