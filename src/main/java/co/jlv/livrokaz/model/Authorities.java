package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private int authorityId;
	
	//bi-directional many-to-one association to Users
//	@ManyToOne
//	@JoinColumn(name="username")
//	private Users users;


	
	@Id
	@Column(name = "username")
	private String username;

	//one-to-one-directional association to User
	@MapsId()
	@OneToOne(optional=true, cascade=CascadeType.REMOVE)
	@JoinColumn(name="username", unique=true, nullable=false, updatable=false)
    private Users users;

	 
	private String authority;
	
	
	public Authorities(String authority) {
		this.authority = authority;
	}

	public Authorities() {
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