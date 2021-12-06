import java.util.Comparator;

public class PriceCompare implements Comparator<Book>{
	
	public int compare(Book bk1, Book bk2) {
		if (bk1.getRetailPrice() < bk2.getRetailPrice()) {
			return -1;
		} if (bk1.getRetailPrice() > bk2.getRetailPrice() ) {
			return 1;
		} else {
			return 0;
		}
	}


}