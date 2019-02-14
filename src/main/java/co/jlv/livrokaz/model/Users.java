package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable, UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "id", unique = true)
    private long id;
	
	@Id
	@Column(unique=true)
	@NotBlank(message = "Login ne doit pas être vide")
	private String username;
	
	//bi-directional many-to-one association to Ordering
	@OneToMany(mappedBy="users")
	private List<Ordering> orderings;
	
	@NotBlank(message = "Mot de passe ne doit pas être vide")
	private  String password;
	
	private boolean enabled;
	
	private String civility;

	
	private int yyyy;
	

	private int mm;
	
	private int dd;

	private String firstName;

	private String lastName;

	private String tel;
	
	private String numVoieDomicile;

	private String nomVoieDomicile;

	private int cpDomicile;

	private String cityDomicile;

	private String countryDomicile;
	
	private String numVoieLivraison;


	private String nomVoieLivraison;

	private int cpLivraison;

	private String cityLivraison;

	private String countryLivraison;


	

	public Users() {
	}
	
	public Users(Long id, String username, String password, boolean enabled, 
			String civility, String firstName, String lastName, String tel, int yyyy,int mm, int dd,
			String numVoieDomicile, String nomVoieDomicile, int cpDomicile, String cityDomicile, String countryDomicile,
			String numVoieLivraison, String nomVoieLivraison, int cpLivraison, String cityLivraison, String countryLivraison
			) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.civility = civility;
		this.firstName = firstName;
		this.lastName = lastName;
		this.tel = tel;
		this.yyyy = yyyy;
		this.mm= mm;
		this.dd=dd;
		this.numVoieDomicile = numVoieDomicile;
		this.nomVoieDomicile = nomVoieDomicile;
		this.cpDomicile = cpDomicile;
		this.cityDomicile = cityDomicile;
		this.countryDomicile = countryDomicile;
		this.numVoieLivraison = numVoieLivraison;
		this.nomVoieLivraison = nomVoieLivraison;
		this.cpLivraison = cpLivraison;
		this.cityLivraison = cityLivraison;
		this.countryLivraison = countryLivraison;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public String getNumVoieDomicile() {
		return numVoieDomicile;
	}

	public void setNumVoieDomicile(String numVoieDomicile) {
		this.numVoieDomicile = numVoieDomicile;
	}

	public String getNomVoieDomicile() {
		return nomVoieDomicile;
	}

	public void setNomVoieDomicile(String nomVoieDomicile) {
		this.nomVoieDomicile = nomVoieDomicile;
	}

	public int getCpDomicile() {
		return cpDomicile;
	}

	public void setCpDomicile(int cpDomicile) {
		this.cpDomicile = cpDomicile;
	}

	public String getCityDomicile() {
		return cityDomicile;
	}

	public void setCityDomicile(String cityDomicile) {
		this.cityDomicile = cityDomicile;
	}

	public String getCountryDomicile() {
		return countryDomicile;
	}

	public void setCountryDomicile(String countryDomicile) {
		this.countryDomicile = countryDomicile;
	}

	public String getNumVoieLivraison() {
		return numVoieLivraison;
	}

	public void setNumVoieLivraison(String numVoieLivraison) {
		this.numVoieLivraison = numVoieLivraison;
	}

	public String getNomVoieLivraison() {
		return nomVoieLivraison;
	}

	public void setNomVoieLivraison(String nomVoieLivraison) {
		this.nomVoieLivraison = nomVoieLivraison;
	}

	public int getCpLivraison() {
		return cpLivraison;
	}

	public void setCpLivraison(int cpLivraison) {
		this.cpLivraison = cpLivraison;
	}

	public String getCityLivraison() {
		return cityLivraison;
	}

	public void setCityLivraison(String cityLivraison) {
		this.cityLivraison = cityLivraison;
	}

	public String getCountryLivraison() {
		return countryLivraison;
	}

	public void setCountryLivraison(String countryLivraison) {
		this.countryLivraison = countryLivraison;
	}

	public int getYyyy() {
		return yyyy;
	}

	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}

	public int getMm() {
		return mm;
	}

	public void setMm(int mm) {
		this.mm = mm;
	}

	public int getDd() {
		return dd;
	}

	public void setDd(int dd) {
		this.dd = dd;
	}

	
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
	
	@Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}