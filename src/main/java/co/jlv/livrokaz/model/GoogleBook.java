package co.jlv.livrokaz.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int bookId;

	private int availableQuantity;

	private String categorie;

	private String codeIsbn;

	@Lob
	private String description;

	@Lob
	private String imgThumbnail;

	private boolean isEbook;

	private String langage;

	private int pageCount;

	private double price;

	//@Temporal(TemporalType.DATE)
	private String publishReleased;

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
	
	

	/*public GoogleBook( int availableQuantity, String categorie, String codeISBN, String description,
			String imgThumbnail, boolean isEbook, String langage, int pageCount, double price, String publishReleased,
			String textSnippet, String title, Gendle gendle, Publisher publisher, List<Author> authors/*,
			List<R_Ordering_GoogleBook> rOrderingGoogleBooks*//*) {
		super();
		this.availableQuantity = availableQuantity;
		this.categorie = categorie;
		this.codeISBN = codeISBN;
		this.description = description;
		this.imgThumbnail = imgThumbnail;
		this.isEbook = isEbook;
		this.langage = langage;
		this.pageCount = pageCount;
		this.price = price;
		this.publishReleased = publishReleased;
		this.textSnippet = textSnippet;
		this.title = title;
		this.gendle = gendle;
		this.publisher = publisher;
		this.authors = authors;
		
	}*/
	
	public GoogleBook( 
			Gendle gendle, Publisher publisher, List<Author> authors, String publishReleased,
			int availableQuantity, String categorie, String codeIsbn, String description,
			String imgThumbnail,boolean isEbook,String langage,int pageCount,double price,
			String textSnippet, String title/*,
			List<R_Ordering_GoogleBook> rOrderingGoogleBooks*/) {
		super();
		
		this.gendle = gendle;
		this.publisher = publisher;
		this.authors = authors;
		this.publishReleased = publishReleased;
		this.availableQuantity = availableQuantity;
		this.categorie = categorie;
		this.codeIsbn = codeIsbn;
		this.description = description; 
		this.imgThumbnail = imgThumbnail;
		this.isEbook = isEbook;
		this.langage = langage;
		this.pageCount = pageCount;
		this.price = price;
		this.textSnippet = textSnippet;
		this.title = title;
		//ROrderingGoogleBooks = rOrderingGoogleBooks;
	}
	
	

	public GoogleBook() {
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
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

	public String getCodeIsbn() {
		return this.codeIsbn;
	}

	public void setCodeIsbn(String codeIsbn) {
		this.codeIsbn = codeIsbn ;
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

	public boolean getIsEbook() {
		return this.isEbook;
	}

	public void setIsEbook(boolean isEbook) {
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

	public String getPublishReleased() {
		return this.publishReleased;
	}

	public void setPublishReleased(String publishReleased) {
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

	@JsonIgnore
	public Gendle getGendle() {
		return this.gendle;
	}

	public void setGendle(Gendle gendle) {
		this.gendle = gendle;
	}

	@JsonIgnore
	public Publisher getPublisher() {
		return this.publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	@JsonIgnore	
	public List<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@JsonIgnore
	public List<R_Ordering_GoogleBook> getROrderingGoogleBooks() {
		return this.ROrderingGoogleBooks;
	}

	public void setROrderingGoogleBooks(List<R_Ordering_GoogleBook> ROrderingGoogleBooks) {
		this.ROrderingGoogleBooks = ROrderingGoogleBooks;
	}

	@JsonIgnore
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