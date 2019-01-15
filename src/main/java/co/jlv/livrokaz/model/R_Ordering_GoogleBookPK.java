package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the R_Ordering_GoogleBooks database table.
 * 
 */
@Embeddable
public class R_Ordering_GoogleBookPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String bookId;

	@Column(insertable=false, updatable=false)
	private int orderingId;

	public R_Ordering_GoogleBookPK() {
	}
	public String getBookId() {
		return this.bookId;
	}
	public void setBookId(String bookId) {
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
			this.bookId.equals(castOther.bookId)
			&& (this.orderingId == castOther.orderingId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bookId.hashCode();
		hash = hash * prime + this.orderingId;
		
		return hash;
	}
}