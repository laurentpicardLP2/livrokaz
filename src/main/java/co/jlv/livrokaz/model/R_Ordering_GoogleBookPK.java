package co.jlv.livrokaz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the R_Ordering_GoogleBooks database table.
 * 
 */
@Embeddable
public class R_Ordering_GoogleBookPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int bookId;

	@Column(insertable=false, updatable=false)
	private int orderingId;

	public R_Ordering_GoogleBookPK() {
	}
	public int getBookId() {
		return this.bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getOrderingId() {
		return this.orderingId;
	}
	public void setOrderingId(int orderingId) {
		this.orderingId = orderingId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof R_Ordering_GoogleBookPK)) {
			return false;
		}
		R_Ordering_GoogleBookPK castOther = (R_Ordering_GoogleBookPK)other;
		return 
			this.bookId==castOther.bookId
			&& (this.orderingId == castOther.orderingId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderingId;
		
		return hash;
	}
}