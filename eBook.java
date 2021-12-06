
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class eBook extends Book{
	private int noOfPages;
	private eBookFormat format;
	private BookType type = BookType.eBook; 
	
	public eBook(String ISBN, String title, Language language, Genre genre, LocalDate releaseDate, float retailPrice, int stock, int noOfPages, eBookFormat format) {
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.noOfPages = noOfPages;
		this.format = format;
	}
	
	//Getter Methods
	public int getNoOfPages() {
		return noOfPages;
	} public eBookFormat getFormat() {
		return format;
	} public BookType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return getISBN() + ", ebook, " + getTitle() + ", " + getLanguage() + ", " + genreToString() +
				", " + formatter.format(getDate()) + ", " + getRetailPrice() + ", " + getStock() + ", " + noOfPages + ", " + format;
	}
}
