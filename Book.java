import java.time.LocalDate;


public class Book {
	private final String ISBN;
	private String title;
	private Language language;
	private Genre genre;
	private LocalDate releaseDate;
	private float retailPrice;
	private int stock;
	
	public Book(String ISBN, String title, Language language, Genre genre, LocalDate releaseDate, float retailPrice, int stock) {
		this.ISBN = ISBN;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.retailPrice = retailPrice;
		this.stock = stock;
	}
	//Getter Methods
	public String getISBN() {
		return ISBN;
	} public String getTitle() {
		return title;
	} public Language getLanguage() {
		return language;
	} public Genre getGenre() {
		return genre;
	} public LocalDate getDate() {
		return releaseDate;
	}	public float getRetailPrice() {
		return retailPrice;
	} public int getStock() {
		return stock;
	} 
	
	//Setter Methods
	public void increaseStock(int quantity) {
		this.stock = this.stock + quantity;
	} public void decreaseStock(int quantity) {
		this.stock = this.stock - quantity;
	}
	
	public String genreToString(){
		String string = null;
		if (this.genre == Genre.ComputerScience) {
			string = "Computer Science";
		} else if (this.genre == Genre.Biography) {
			string = "Biography";
		} else if (this.genre == Genre.Business) {
			string = "Business";
		} else if (this.genre == Genre.Medicine) {
			string = "Medicine";
		} else if (this.genre == Genre.Politics) {
			string = "Politics";
		} 
		return string;
	}
	
}
