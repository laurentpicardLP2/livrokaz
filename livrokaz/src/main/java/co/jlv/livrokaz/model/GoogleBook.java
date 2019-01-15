package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GoogleBooks database table.
 * 
 */
@Entity
@Table(name="GoogleBooks")
@NamedQuery(name="GoogleBook.findAll", query="SELECT g FROM GoogleBook g")
public class GoogleBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String bookId;

	private int availableQuantity;

	private String categorie;

	private String codeISBN;

	@Lob
	private String description;

	@Lob
	private String imgThumbnail;

	private byte isEbook;

	private String langage;

	private int pageCount;

	private double price;

	@Temporal(TemporalType.DATE)
	private Date publishReleased;

	@Lob
	private String textSnippet;

	private String title;

	//bi-directional many-to-one association to Gendle
	@ManyToOne
	@JoinColumn(name="gendleId")
	private Gendle gendle;

	//bi-directional many-to-one association to Publisher
	@ManyToOne
	@JoinColumn(name="publisherId")
	private Publisher publisher;

	//bi-directional many-to-many association to Author
	@ManyToMany
	@JoinTable(
		name="R_GoogleBooks_Author"
		, joinColumns={
			@JoinColumn(name="bookId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="authorId")
			}
		)
	private List<Author> authors;

	//bi-directional many-to-one association to R_Ordering_GoogleBook
	@OneToMany(mappedBy="googleBook")
	private List<R_Ordering_GoogleBook> ROrderingGoogleBooks;

	public GoogleBook() {
	}

	public String getBookId() {
		return this.bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getAvailableQuantity() {
		return this.availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getCodeISBN() {
		return this.codeISBN;
	}

	public void setCodeISBN(String codeISBN) {
		this.codeISBN = codeISBN;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgThumbnail() {
		return this.imgThumbnail;
	}

	public void setImgThumbnail(String imgThumbnail) {
		this.imgThumbnail = imgThumbnail;
	}

	public byte getIsEbook() {
		return this.isEbook;
	}

	public void setIsEbook(byte isEbook) {
		this.isEbook = isEbook;
	}

	public String getLangage() {
		return this.langage;
	}

	public void setLangage(String langage) {
		this.langage = langage;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPublishReleased() {
		return this.publishReleased;
	}

	public void setPublishReleased(Date publishReleased) {
		this.publishReleased = publishReleased;
	}

	public String getTextSnippet() {
		return this.textSnippet;
	}

	public void setTextSnippet(String textSnippet) {
		this.textSnippet = textSnippet;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Gendle getGendle() {
		return this.gendle;
	}

	public void setGendle(Gendle gendle) {
		this.gendle = gendle;
	}

	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<R_Ordering_GoogleBook> getROrderingGoogleBooks() {
		return this.ROrderingGoogleBooks;
	}

	public void setROrderingGoogleBooks(List<R_Ordering_GoogleBook> ROrderingGoogleBooks) {
		this.ROrderingGoogleBooks = ROrderingGoogleBooks;
	}

	public R_Ordering_GoogleBook addROrderingGoogleBook(R_Ordering_GoogleBook ROrderingGoogleBook) {
		getROrderingGoogleBooks().add(ROrderingGoogleBook);
		ROrderingGoogleBook.setGoogleBook(this);

		return ROrderingGoogleBook;
	}

	public R_Ordering_GoogleBook removeROrderingGoogleBook(R_Ordering_GoogleBook ROrderingGoogleBook) {
		getROrderingGoogleBooks().remove(ROrderingGoogleBook);
		ROrderingGoogleBook.setGoogleBook(null);

		return ROrderingGoogleBook;
	}

}