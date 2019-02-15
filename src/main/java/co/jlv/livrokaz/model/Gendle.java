package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the Gendle database table.
 * 
 */
@Entity
@NamedQuery(name="Gendle.findAll", query="SELECT g FROM Gendle g")
public class Gendle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int gendleId;

	private String typeGendle;
	
	private int nbBooks;

	//bi-directional many-to-one association to GoogleBook
	@OneToMany(mappedBy="gendle")
	private List<GoogleBook> googleBooks;

	public Gendle() {
	}
	
	public Gendle(String typeGendle) {
		this.typeGendle = typeGendle;
	}
	
	public Gendle(String typeGendle, int nbBooks) {
		this.typeGendle = typeGendle;
		this.nbBooks = nbBooks;
	}


	public int getGendleId() {
		return this.gendleId;
	}

	public void setGendleId(int gendleId) {
		this.gendleId = gendleId;
	}

	public String getTypeGendle() {
		return this.typeGendle;
	}

	public void setTypeGendle(String typeGendle) {
		this.typeGendle = typeGendle;
	}
	
	
	public int getNbBooks() {
		return nbBooks;
	}

	public void setNbBooks(int nbBooks) {
		this.nbBooks = nbBooks;
	}

	@JsonIgnore
	public List<GoogleBook> getGoogleBooks() {
		return this.googleBooks;
	}

	public void setGoogleBooks(List<GoogleBook> googleBooks) {
		this.googleBooks = googleBooks;
	}

	public GoogleBook addGoogleBook(GoogleBook googleBook) {
		getGoogleBooks().add(googleBook);
		googleBook.setGendle(this);

		return googleBook;
	}

	public GoogleBook removeGoogleBook(GoogleBook googleBook) {
		getGoogleBooks().remove(googleBook);
		googleBook.setGendle(null);

		return googleBook;
	}

}