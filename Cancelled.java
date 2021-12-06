import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cancelled extends Transaction{
	private LocalDate releaseDate = LocalDate.now();
	
	public Cancelled(int userId, String postcode, String ISBN, float retailPrice, int quantity) {
		super(userId, postcode, ISBN, retailPrice, quantity);
		
	}
	
	//Getter Methods
	public LocalDate getReleaseDate() {
		return releaseDate;
	} 
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return getUserId() +", "+ getPostcode() +", "+ getISBN() +", "+ getRetailPrice() +", "+getQuantity() +", cancelled, , "+ formatter.format(getReleaseDate());
	}
}
