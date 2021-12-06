import java.util.Comparator;

public class BookTypeCompare implements Comparator<Book>{
	
	public int compare(Book bk1, Book bk2) {
		String[] x = bk1.toString().split(",");
		String[] y = bk2.toString().split(",");
		return (x[1].trim().compareTo(y[1].trim()));
	}
}
