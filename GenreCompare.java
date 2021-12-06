import java.util.Comparator;

public class GenreCompare implements Comparator<Book>{
	
	public int compare(Book bk1, Book bk2) {
		return bk1.genreToString().compareTo(bk2.genreToString());
	}

}
