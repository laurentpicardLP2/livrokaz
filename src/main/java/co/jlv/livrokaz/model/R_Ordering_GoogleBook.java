package co.jlv.livrokaz.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the R_Ordering_GoogleBooks database table.
 * 
 */
@Entity
@Table(name="R_Ordering_GoogleBooks")
@NamedQuery(name="R_Ordering_GoogleBook.findAll", query="SELECT r FROM R_Ordering_GoogleBook r")
public class R_Ordering_GoogleBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private R_Ordering_GoogleBookPK id;

	private int quantity;

	//bi-directional many-to-one association to GoogleBook
	@ManyToOne
	@JoinColumn(name="bookId")
	private GoogleBook googleBook;

	//bi-directional many-to-one association to Ordering
	@ManyToOne
	@JoinColumn(name="orderingId")
	private Ordering ordering;

	public R_Ordering_GoogleBook() {
	}

	public R_Ordering_GoogleBookPK getId() {
		return this.id;
	}

	public void setId(R_Ordering_GoogleBookPK id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public GoogleBook getGoogleBook() {
		return this.googleBook;
	}

	public void setGoogleBook(GoogleBook googleBook) {
		this.googleBook = googleBook;
	}

	public Ordering getOrdering() {
		return this.ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

}