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
 * The persistent class for the Publisher database table.
 * 
 */
@Entity
@NamedQuery(name="Publisher.findAll", query="SELECT p FROM Publisher p")
public class Publisher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int publisherId;

	private String namePublisher;

	//bi-directional many-to-one association to GoogleBook
	@OneToMany(mappedBy="publisher")
	private List<GoogleBook> googleBooks;

	public Publisher() {
	}

	public int getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getNamePublisher() {
		return this.namePublisher;
	}

	public void setNamePublisher(String namePublisher) {
		this.namePublisher = namePublisher;
	}

	public List<GoogleBook> getGoogleBooks() {
		return this.googleBooks;
	}

	public void setGoogleBooks(List<GoogleBook> googleBooks) {
		this.googleBooks = googleBooks;
	}

	public GoogleBook addGoogleBook(GoogleBook googleBook) {
		getGoogleBooks().add(googleBook);
		googleBook.setPublisher(this);

		return googleBook;
	}

	public GoogleBook removeGoogleBook(GoogleBook googleBook) {
		getGoogleBooks().remove(googleBook);
		googleBook.setPublisher(null);

		return googleBook;
	}

}