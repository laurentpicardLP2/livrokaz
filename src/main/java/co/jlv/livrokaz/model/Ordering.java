package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Ordering database table.
 * 
 */
@Entity
@NamedQuery(name="Ordering.findAll", query="SELECT o FROM Ordering o")
public class Ordering implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderingId;

	@Temporal(TemporalType.DATE)
	private Date dateDelivery;

	@Temporal(TemporalType.DATE)
	private Date dateOrdering;

	private double shippingFees;

	private double totalAmount;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId_R_Ordering_User")
	private User user;

	//bi-directional many-to-one association to R_Ordering_GoogleBook
	@OneToMany(mappedBy="ordering")
	private List<R_Ordering_GoogleBook> ROrderingGoogleBooks;

	public Ordering() {
	}

	public int getOrderingId() {
		return this.orderingId;
	}

	public void setOrderingId(int orderingId) {
		this.orderingId = orderingId;
	}

	public Date getDateDelivery() {
		return this.dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateOrdering() {
		return this.dateOrdering;
	}

	public void setDateOrdering(Date dateOrdering) {
		this.dateOrdering = dateOrdering;
	}

	public double getShippingFees() {
		return this.shippingFees;
	}

	public void setShippingFees(double shippingFees) {
		this.shippingFees = shippingFees;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<R_Ordering_GoogleBook> getROrderingGoogleBooks() {
		return this.ROrderingGoogleBooks;
	}

	public void setROrderingGoogleBooks(List<R_Ordering_GoogleBook> ROrderingGoogleBooks) {
		this.ROrderingGoogleBooks = ROrderingGoogleBooks;
	}

	public R_Ordering_GoogleBook addROrderingGoogleBook(R_Ordering_GoogleBook ROrderingGoogleBook) {
		getROrderingGoogleBooks().add(ROrderingGoogleBook);
		ROrderingGoogleBook.setOrdering(this);

		return ROrderingGoogleBook;
	}

	public R_Ordering_GoogleBook removeROrderingGoogleBook(R_Ordering_GoogleBook ROrderingGoogleBook) {
		getROrderingGoogleBooks().remove(ROrderingGoogleBook);
		ROrderingGoogleBook.setOrdering(null);

		return ROrderingGoogleBook;
	}

}