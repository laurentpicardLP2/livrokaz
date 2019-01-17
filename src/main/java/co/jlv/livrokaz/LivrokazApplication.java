package co.jlv.livrokaz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import co.jlv.livrokaz.model.Author;
import co.jlv.livrokaz.model.Gendle;
import co.jlv.livrokaz.model.GoogleBook;
import co.jlv.livrokaz.model.Publisher;
import co.jlv.livrokaz.repository.AuthorRepository;
import co.jlv.livrokaz.repository.GendleRepository;
import co.jlv.livrokaz.repository.GoogleBookRepository;
import co.jlv.livrokaz.repository.PublisherRepository;


@SpringBootApplication
public class LivrokazApplication implements CommandLineRunner {

	@Autowired
	private GoogleBookRepository googleBookRepo;

	@Autowired
	private GendleRepository gendleRepo;

	@Autowired
	private PublisherRepository publisherRepo;

	@Autowired
	private AuthorRepository authorRepo;

	public static void main(String[] args) {
		SpringApplication.run(LivrokazApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception, JSONException, MalformedURLException, IOException, ClassNotFoundException, SQLException  {
		GoogleBook gb;
		Gendle gendle;
		Publisher publisher;
		Author author;
		List<Author> authors = new ArrayList<Author>();
		String sDate;
		int availableQuantity;
		String categorie;
		String codeISBN;
		String description;
		String imgThumbnail;
		boolean isEbook;
		String langage;
		int pageCount;
		double price;
		String textSnippet;
		String title;

		String url = "https://www.googleapis.com/books/v1/volumes?q=cooking&maxResults=40&key=AIzaSyAPOsreRHHdYcdZ4pX7YNXBujTndpGJF9k";

		String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		GoogleBook googleBooks = new GoogleBook();

		String jsonTxt = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));

		JSONObject json = new JSONObject(jsonTxt);
		JSONArray items = null;
		JSONObject item = null;
		JSONObject searchInfo = null;

		try {
			items = json.getJSONArray("items");
			item = (JSONObject) items.get(5);

		} catch(JSONException e) {  
		}

		try {
			searchInfo = item.getJSONObject("searchInfo");
		} catch(JSONException e) {
		}

		JSONObject saleInfo = item.getJSONObject("saleInfo");
		JSONObject volumeInfo = item.getJSONObject("volumeInfo");
		JSONObject imageLink = volumeInfo.getJSONObject("imageLinks");
		JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
		JSONObject ISBN_1010 = (JSONObject) industryIdentifiers.get(0);


/*****************************************************************
**************************** GENDLE ******************************
******************************************************************/

		gendle=gendleRepo.findByGendle("Cuisine");
		if(gendle==null) {
			gendleRepo.save(new Gendle("Cuisine"));
			gendle=gendleRepo.findByGendle("Cuisine");
		}

/*****************************************************************
************************* PUBLISHER ******************************
*******************************************************************/

		String publisherS = "";


		try {
			publisherS = volumeInfo.getString("publisher");

		} catch(JSONException e) {
			publisherS = "unknown";

		}finally {

			publisherS = volumeInfo.getString("publisher");
			publisher = publisherRepo.findByPublisher(publisherS);
			if(publisher == null) {
				publisherRepo.save(new Publisher(publisherS));
			}
		}

/*****************************************************************
************************* AUTHORS ********************************
******************************************************************/
		
		JSONArray authorsAr = null;
		//authors = null;

		try {
			authorsAr = volumeInfo.getJSONArray("authors"); 
			for (int i = 0; i < authorsAr.length(); i++) {

				author = authorRepo.findByAuthor(authorsAr.getString(i));
				if(author == null) {
					authorRepo.save(new Author(authorsAr.getString(i)));
				}
				authors.add(authorRepo.findByAuthor(authorsAr.getString(i)));
			}

		} catch(JSONException e) {
			authors = null;

		}

/*****************************************************************
************************** TITLE *********************************
******************************************************************/
		title = volumeInfo.getString("title");

/*****************************************************************
*********************** PUBLISHED DATE ***************************
******************************************************************/ 
		sDate = "";

		try {
			sDate = volumeInfo.getString("publishedDate");

		} catch (JSONException e) {
			sDate = "unknown";

		} 

/*****************************************************************
************************ ISBN_10 *********************************
******************************************************************/
		codeISBN = "";

		try {
			codeISBN = ISBN_1010.getString("identifier");
			codeISBN = codeISBN.substring(codeISBN.indexOf(":")+1);


		}catch(JSONException e) {
			codeISBN = "unknown";

		}

/*****************************************************************
************************* PAGE COUNT *****************************
******************************************************************/
		pageCount = 0;

		try {
			pageCount = volumeInfo.getInt("pageCount");

		}catch(JSONException e) {
			pageCount = 0;

		}

/*****************************************************************
************************** THUMBNAIL *****************************
******************************************************************/
		imgThumbnail = "";

		try {
			imgThumbnail = imageLink.getString("smallThumbnail");

		}catch(JSONException e) {
			imgThumbnail = "can't find thumbnail";

		}


/*****************************************************************
**************************** PRICE *******************************
******************************************************************/
		price = 0;

		try {
			JSONObject listPrice = saleInfo.getJSONObject("listPrice");
			price = listPrice.getDouble("amount");

		} catch(JSONException e) {
			price =0;

		} 
/*****************************************************************
*************************** SNIPPET ******************************
******************************************************************/
		textSnippet = "";

		try {
			textSnippet = searchInfo.getString("textSnippet");
		} catch (JSONException e) {

			textSnippet = "can't find snippet";
		}catch(NullPointerException n) {
			textSnippet = "can't find snippet";
		}



/*****************************************************************
************************* DESCRIPTION ****************************
******************************************************************/
		description = "";

		try {
			description = volumeInfo.getString("description");
		} catch (JSONException e) {
			description = "can't find description";

		} catch(NullPointerException n) {
			description = "can't find description";

		}

/*****************************************************************
*************************** eBOOK ********************************
******************************************************************/
		isEbook = true;

		try {
			isEbook = saleInfo.getBoolean("isEbook");

		} catch(JSONException e) {
			isEbook = false;
		}

		availableQuantity = 10;
		categorie = "Cuisine";
		langage = "fr";

		gb = new GoogleBook(gendle, publisher, authors, sDate, 
				availableQuantity, categorie, codeISBN, description, imgThumbnail,
				isEbook, langage, pageCount, price, textSnippet, title);
		googleBookRepo.save(gb);


	}
}
