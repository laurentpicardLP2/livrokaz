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
	@Column(name = "usersId", unique = true)
    private long usersId;
	
	@Id
	@Column(unique=true)
	@NotBlank(message = "Login ne doit pas être vide")
	private String username;
	
	//bi-directional many-to-one association to Ordering
	@OneToMany(mappedBy="users")
	private List<Ordering> orderings;
	
	@NotBlank(message = "Mot de passe ne doit pas être vide")
	private  String password;
	
	private String email;
	
	private boolean enabled;

	private String fullName;

	private String domesticAddress;

	private String domesticCp;

	private String domesticCity;

	private String domesticCountry;

	private String deliveryAddress;

	private String deliveryCp;

	private String deliveryCity;

	private String deliveryCountry;

	private String telephone;
	
	private Date dateOfBirth;

	public Users() {
	}
	
	public Users(Long usersId, boolean enabled, String fullName, String username, String email, String password, 
			String domesticAddress, String domesticCp, String domesticCity, String domesticCountry,
			String deliveryAddress, String deliveryCp, String deliveryCity, String deliveryCountry, 
			String telephone, Date dateOfBirth
			) {
		this.usersId = usersId;
		this.enabled = enabled;
		this.fullName = fullName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.domesticAddress = domesticAddress;
		this.domesticCp = domesticCp;
		this.domesticCity = domesticCity;
		this.domesticCountry = domesticCountry;
		this.deliveryAddress = deliveryAddress;
		this.deliveryCp = deliveryCp;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.telephone = telephone;
		this.dateOfBirth = dateOfBirth;
	}
	
	public long getUsersId() {
		return usersId;
	}

	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDomesticAddress() {
		return domesticAddress;
	}

	public void setDomesticAddress(String domesticAddress) {
		this.domesticAddress = domesticAddress;
	}

	public String getDomesticCp() {
		return domesticCp;
	}

	public void setDomesticCp(String domesticCp) {
		this.domesticCp = domesticCp;
	}

	public String getDomesticCity() {
		return domesticCity;
	}

	public void setDomesticCity(String domesticCity) {
		this.domesticCity = domesticCity;
	}

	public String getDomesticCountry() {
		return domesticCountry;
	}

	public void setDomesticCountry(String domesticCountry) {
		this.domesticCountry = domesticCountry;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryCp() {
		return deliveryCp;
	}

	public void setDeliveryCp(String deliveryCp) {
		this.deliveryCp = deliveryCp;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public void setDeliveryCountry(String deliveryCountry) {
		this.deliveryCountry = deliveryCountry;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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