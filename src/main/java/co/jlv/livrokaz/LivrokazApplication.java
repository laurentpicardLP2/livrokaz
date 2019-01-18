package co.jlv.livrokaz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
		Publisher publisher = null;
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
		
		
		List<String> listCat = Arrays.asList("cooking", "thriller", "economics", "novels", "comics");
		for(String entryCat : listCat) {
		
			String url = "https://www.googleapis.com/books/v1/volumes?q=" + entryCat + "&maxResults=4&key=AIzaSyAPOsreRHHdYcdZ4pX7YNXBujTndpGJF9k";
	
			String jsonText = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
	
			GoogleBook googleBooks = new GoogleBook();
	
			String jsonTxt = IOUtils.toString(new URL(url), Charset.forName("UTF-8"));
	
			JSONObject json = new JSONObject(jsonTxt);
			JSONArray items = null;
			JSONObject item = null;
			JSONObject searchInfo = null;
			JSONObject volumeInfo=null;
			JSONObject imageLink=null;
			JSONArray industryIdentifiers=null;
			JSONObject ISBN_1010=null;
			JSONObject saleInfo=null;
			
	
			for(int v=0; v < json.getJSONArray("items").length(); v++) {
				try {
					items = json.getJSONArray("items");
					item = (JSONObject) items.get(v);
		
				} catch(Exception e) {
				}
		
				try {
					searchInfo = item.getJSONObject("searchInfo");
				} catch(Exception e) {  
				}
				
				try {
					saleInfo = item.getJSONObject("saleInfo");
				}catch(Exception e) {  
				}
				try {
					volumeInfo = item.getJSONObject("volumeInfo");
				}catch(JSONException e) {  
				}
				try {
					imageLink = volumeInfo.getJSONObject("imageLinks");
				}catch(JSONException e) {  
				}
				try {
					industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
				}catch(JSONException e) {  
				}
				
				if (industryIdentifiers!=null) {
					try {
						ISBN_1010 = (JSONObject) industryIdentifiers.get(0);
					}catch(JSONException e) {  
					}
				}
				
		
		
		
		/*****************************************************************
		**************************** GENDLE ******************************
		******************************************************************/
		
				gendle=gendleRepo.findByGendle(entryCat);
				if(gendle==null) {
					gendleRepo.save(new Gendle(entryCat));
					gendle=gendleRepo.findByGendle(entryCat);
				}
		
		/*****************************************************************
		************************* PUBLISHER ******************************
		*******************************************************************/
		
				
				String publisherS="";
		
				if(volumeInfo!=null) {
						try {
						publisherS = volumeInfo.getString("publisher");
			
					} catch(JSONException e) {
						publisherS = "unknown";
			
					}finally {
						
						publisher = publisherRepo.findByPublisher(publisherS);
						if(publisher == null) {
							publisherRepo.save(new Publisher(publisherS));
						}
					}
					
				}else {
					if(publisher == null) {
						publisherRepo.save(new Publisher(publisherS));
					}
				}
		
		/*****************************************************************
		************************* AUTHORS ********************************
		******************************************************************/
				
				JSONArray authorsAr = null;
				//authors = null;
				
				
				if(volumeInfo!=null) {
					try {
						authorsAr=null;
						authors.clear();
						authorsAr = volumeInfo.getJSONArray("authors"); 
						/*for(int i= 0; i<authorsAr.length();i++) {
							System.out.println("************************************ " + v);
							System.out.println(authorsAr.getString(i));
							System.out.println("************************************ " + v);
						}*/
						
							for (int i = 0; i < authorsAr.length(); i++) {
				
								author = authorRepo.findByAuthor(authorsAr.getString(i));
								if(author == null) {
									authorRepo.save(new Author(authorsAr.getString(i)));
								}
								try {
									authors.add(authorRepo.findByAuthor(authorsAr.getString(i)));
								} catch(Exception e) {
									authors = null;
								}
								
									
							}
						
			
					} catch(Exception e) {
						authors = null;
			
					}
				}else {
					authors = null;
				}
					
		
		/*****************************************************************
		************************** TITLE *********************************
		******************************************************************/
				if(volumeInfo!=null) {
					try {
						title = volumeInfo.getString("title");
					} catch(Exception e) {
						title="";
					}
				}else {
					title=""; 
				}
		
		/*****************************************************************
		*********************** PUBLISHED DATE ***************************
		******************************************************************/ 
				sDate = "";
				if(volumeInfo!=null) {
					try {
						sDate = volumeInfo.getString("publishedDate");
			
					} catch (JSONException e) {
						sDate = "unknown";
			
					} 
				} 
		
		/*****************************************************************
		************************ ISBN_10 *********************************
		******************************************************************/
				codeISBN = "";
				if(ISBN_1010!=null) {
					try {
						codeISBN = ISBN_1010.getString("identifier");
						codeISBN = codeISBN.substring(codeISBN.indexOf(":")+1);
			
			
					}catch(JSONException e) {
						codeISBN = "unknown";
			
					}
				}
		
		/*****************************************************************
		************************* PAGE COUNT *****************************
		******************************************************************/
				pageCount = 0;
		
				if(volumeInfo!=null) {
					try {
						pageCount = volumeInfo.getInt("pageCount");
			
					}catch(JSONException e) {
						pageCount = 0;
			
					}
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
		
				if(saleInfo!=null) {
					try {
						JSONObject listPrice = saleInfo.getJSONObject("listPrice");
						price = listPrice.getDouble("amount");
			
					} catch(JSONException e) {
						price = 0;
			
					} 
			}
				if(price == 0) {
					int ent = (int) Math.floor(Math.random() * 100);
					int dec = (int) Math.floor(Math.random() * 100);
					price = ent + (dec * 0.01);
				}
				
		/*****************************************************************
		*************************** SNIPPET ******************************
		******************************************************************/
				textSnippet = "";
		
				if(searchInfo!=null) {
					try {
						textSnippet = searchInfo.getString("textSnippet");
					} catch (JSONException e) {
			
						textSnippet = "can't find snippet";
					}catch(NullPointerException n) {
						textSnippet = "can't find snippet";
					}
				}
		
		
		
		/*****************************************************************
		************************* DESCRIPTION ****************************
		******************************************************************/
				description = "can't find description";
		
				if(volumeInfo!=null) {
					try {
						description = volumeInfo.getString("description");
					} catch (JSONException e) {
						description = "can't find description";
			
					} catch(NullPointerException n) {
						description = "can't find description";
			
					}
				}
		
		/*****************************************************************
		*************************** eBOOK ********************************
		******************************************************************/
				isEbook = true;
		
				if(saleInfo!=null) {
					try {
						isEbook = saleInfo.getBoolean("isEbook");
			
					} catch(JSONException e) {
						isEbook = false;
					}
				}
		
				availableQuantity = 10;
				categorie = entryCat;
				langage = "en";
		
				gb = new GoogleBook(gendle, publisher, authors, sDate, 
						availableQuantity, categorie, codeISBN, description, imgThumbnail,
						isEbook, langage, pageCount, price, textSnippet, title);
				googleBookRepo.save(gb);
	
			}
		}
	}
}
