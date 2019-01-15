package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Author database table.
 * 
 */
@Entity
@NamedQuery(name="Author.findAll", query="SELECT a FROM Author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int authorId;

	private String fullName;

	//bi-directional many-to-many association to GoogleBook
	@ManyToMany(mappedBy="authors")
	private List<GoogleBook> googleBooks;

	public Author() {
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

	public List<GoogleBook> getGoogleBooks() {
		return this.googleBooks;
	}

	public void setGoogleBooks(List<GoogleBook> googleBooks) {
		this.googleBooks = googleBooks;
	}

}