
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AudioBook extends Book{
	private float listeningLength;
	private AudioFormat format;
	private BookType type = BookType.AudioBook; 
	
	public AudioBook(String ISBN, String title, Language language, Genre genre, LocalDate releaseDate, float retailPrice, int stock, float listeningLength, AudioFormat format) {
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.listeningLength = listeningLength;
		this.format = format;
	}
	
	//Getter Methods
	public float getListeningLength() {
		return listeningLength;
	} public AudioFormat getFormat() {
		return format;
	} public BookType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return getISBN() + ", audiobook, " + getTitle() + ", " + getLanguage() + ", " + genreToString() +
				", " + formatter.format(getDate()) + ", " + getRetailPrice() + ", " + getStock() + ", " + listeningLength + ", " + format;
	}

}
