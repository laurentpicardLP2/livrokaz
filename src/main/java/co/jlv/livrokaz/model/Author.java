package co.jlv.livrokaz.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;




/**
 * The persistent class for the Author database table.
 * 
 */
@Entity
@NamedQuery(name="Author.findAll", query="SELECT a FROM Author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int authorId;

	private String fullName;

	//bi-directional many-to-many association to GoogleBook
	@ManyToMany(mappedBy="authors")
	private List<GoogleBook> googleBooks;

	public Author() {
	}
	
	

	public Author(String fullName) {
		super();
		this.fullName = fullName;
	}



	public int getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonIgnore
	public List<GoogleBook> getGoogleBooks() {
		return this.googleBooks;
	}

	public void setGoogleBooks(List<GoogleBook> googleBooks) {
		this.googleBooks = googleBooks;
	}

}