
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Paperback extends Book{
	private int noOfPages;
	private Condition condition;
	private BookType type = BookType.Paperback; 
	
	public Paperback(String ISBN, String title, Language language, Genre genre, LocalDate releaseDate, float retailPrice, int stock, int noOfPages, Condition condition){
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.noOfPages = noOfPages;
		this.condition = condition;
	}
	
	//Getter Methods
	public int getNoOfPages() {
		return noOfPages;
	} public Condition getCondition() {
		return condition;
	} public BookType getType() {
		return type;
	}
	
	public String conditionToString(){
		String string = null;
		if (this.condition == Condition.newBook) {
			string = "new";
		} else if (this.condition == Condition.usedBook) {
			string = "used";
		} 
		return string;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return getISBN() + ", paperback, " + getTitle() + ", " + getLanguage() + ", " + genreToString() +
				", " + formatter.format(getDate()) + ", " + getRetailPrice() + ", " + getStock() + ", " + noOfPages + ", " + conditionToString();
	}


}
